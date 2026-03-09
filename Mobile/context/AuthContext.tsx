// src/context/AuthContext.tsx

import { AuthContextType, User } from "@/types/auth";
import React, { createContext, useContext, useEffect, useState } from "react";


export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  const isAuthenticated = !!token && !!user;

  // Load stored session on app start
  useEffect(() => {
    loadStoredAuth();
  }, []);

  const loadStoredAuth = async () => {
    try {
      const [storedToken, storedUser] = await Promise.all([
        "token: token", // Replace with AsyncStorage.getItem("authToken"),
        "user: user", // Replace with AsyncStorage.getItem("userData"),
        // AsyncStorage.getItem("authToken"),
        // AsyncStorage.getItem("userData"),
      ]);

      if (storedToken && storedUser) {
        setTimeout(()=>{},2000) // Simulate loading delay
        //setToken(storedToken);
        //setUser(JSON.parse(storedUser));
      }
    } finally {
      setLoading(false);
    }
  };

  const login = async (username: string, password: string) => {
    setLoading(true);
    try {
      let b=0
      for (let i = 0; i < 100000000; i++) {
      } // Simulate network delay
      return true
      /*
      // Replace with your API
      const response = await fetch("YOUR_API_ENDPOINT/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        const data = await response.json();

        await Promise.all([
          // AsyncStorage.setItem("authToken", data.token),
          // AsyncStorage.setItem("userData", JSON.stringify(data.user)),
        ]);

        setToken(data.token);
        setUser(data.user);
        return true;
      }
      return false;
      */
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
