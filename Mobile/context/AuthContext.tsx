// src/context/AuthContext.tsx

import { AuthContextType, User } from "@/types/auth";
import React, { createContext, useContext, useEffect, useState } from "react";
import * as SecureStore from 'expo-secure-store';

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  const isAuthenticated = !!token && !!user;

  async function save(key: string, value: string)  {
    await SecureStore.setItemAsync(key, value);
  }

  async function getValueFor(key: string): Promise<string> {
    let result = await SecureStore.getItemAsync(key);
    if (!result) {
      throw new Error(`No values stored under key: ${key}`);
    }
    return result
  }

  useEffect(() => {
    loadStoredAuth();
  }, []);

  const loadStoredAuth = async () => {
    try {
      const [storedToken, storedUser] = await Promise.all([
        getValueFor("authToken"),
        getValueFor("userData"),
      ]);

      if (storedToken && storedUser) {
        setToken(storedToken);
        setUser(JSON.parse(storedUser));
      }
    } catch (error) {
      console.log("Error loading stored auth:", error);
    } finally {
      setLoading(false);
    }
  };

  const login = async (username: string, password: string) => {
    setLoading(true);
    try {
      console.log("logging in")
      /*
      const response = await fetch("YOUR_API_ENDPOINT/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });
      */
      const response = { ok: true, json: async () => ({ token: "", user: { id: 0, username: "test", email: "test@mail.com" } } )};

      if (response.ok) {
        const data = await response.json();

        await Promise.all([
          save("authToken", data.token),
          save("userData", JSON.stringify(data.user)),
        ]);

        setToken(data.token);
        const usr: User = { id: data.user.id, username: data.user.username, email: data.user.email, role: "user" };
        setUser(usr);
        return true;
      }
      return false;
      
    } catch {
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
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{ user, token, isAuthenticated, loading, login, logout }}
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
