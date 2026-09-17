// src/context/AuthContext.tsx

import { AuthContextType } from "@/types/AuthContextType";
import React, { createContext, useContext, useEffect, useState } from "react";
import * as SecureStore from "expo-secure-store";
import { API_BASE } from "@/constants/api";

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined,
);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  const isAuthenticated = !!token 

  async function save(key: string, value: string) {
    await SecureStore.setItemAsync(key, value);
  }

  async function getValueFor(key: string): Promise<string> {
    let result = await SecureStore.getItemAsync(key);
    if (!result) {
      throw new Error(`No values stored under key: ${key}`);
    }
    return result;
  }

  useEffect(() => {
    loadStoredAuth();
  }, []);

  const loadStoredAuth = async () => {
    try {
      const [storedToken] = await Promise.all([
        getValueFor("authToken"),
      ]);

      if (storedToken) {
        const response = await fetch(`${API_BASE}/api/auth`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            
            Cookie: `token=${storedToken}`,
          },
      });
        if (response.ok) {
          setToken(storedToken);
        }
        
      }
    } catch (error) {
      console.log("Error loading or no stored token:", error);
    } finally {
      setLoading(false);
    }
  };

  const login = async (email: string, password: string) => {
    setLoading(true);
    try {
      console.log("logging in");

      const response = await fetch(`${API_BASE}/api/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const tokenObject: String | null = response.headers.get("set-cookie");

        if (tokenObject == null) {
          console.log("Error getting token");
          return false;
        }

        const tokenFields = tokenObject.split(",");
        const tokenWithPrefix = tokenFields[0].split(";")[0];
        const jwt = tokenWithPrefix.split("=")[1];

        console.log(jwt);

        await Promise.all([save("authToken", jwt)]);

        setToken(jwt);
        return true;
      }
      return false;
    } catch (error: any) {
      console.log(error);
      return false;
    } finally {
      setLoading(false);
    }
  };

  const logout = async () => {
    await Promise.all([
      //AsyncStorage.removeItem("authToken"),
      //AsyncStorage.removeItem("userData"),
    ]);
    setToken(null);
  };

  const register = async (email: string, password: string) => {
    setLoading(true);
    try {
      /*
      const response = await fetch("YOUR_API_ENDPOINT/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });
      */
      const response = { ok: true };

      return response.ok;
    } catch {
      return false;
    } finally {
      setLoading(false);
    }
  };


  return (
    <AuthContext.Provider
      value={{ token, isAuthenticated, loading, login, logout, register }}
    >
      {children}
    </AuthContext.Provider>
  );
}
export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used inside AuthProvider");
  return ctx;
};
