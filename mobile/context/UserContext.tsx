
import { createContext, useContext, useEffect, useState } from "react";

import { User } from "@/types/user";

import { useAuth } from "./AuthContext";
import { UserContextType } from "@/types/UserContextType";
import { API_BASE } from "@/constants/api";


export const UserContext = createContext<UserContextType | undefined>(undefined);

export function UserProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | undefined>(undefined);
  const [loading, setLoading] = useState(true);
  const { token } = useAuth();

  const getMe = async (): Promise<User> => {

    if (!token) {
      throw new Error("No auth token available");
    }

    setLoading(true);

    try {
      console.log(`${API_BASE}/api/users/me`)
      const response = await fetch(`${API_BASE}/api/users/me`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Cookie: `token=${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Error getting user");
      }

      const data = await response.json();
      
      const response_user: User = {
        id: data.id,
        email: data.email,
        firstName: data.firstName,
        lastName: data.lastName,
        age: data.age,
        interests: data.interests,
        phoneNumber: data.phoneNumber,
      };

      setUser(response_user);
      return response_user;
    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown user fetch error");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    let isMounted = true;

    const loadUser = async () => {
      if (!token) {
        if (isMounted) {
          setUser(undefined);
          setLoading(false);
        }
        return;
      }

      try {
        const fetchedUser = await getMe();
        if (isMounted) {
          setUser(fetchedUser);
        }
      } catch (error) {
        console.log("Error loading user data:", error);
      }
    };

    loadUser();

    return () => {
      isMounted = false;
    };
  }, [token]);

  return (
    <UserContext.Provider value={{ user, getMe, loading }}>
      {children}
    </UserContext.Provider>
  );
}

export const useUser = () => {
  const ctx = useContext(UserContext);
  if (!ctx) throw new Error("useUser must be used inside UserProvider");
  return ctx;
};
  