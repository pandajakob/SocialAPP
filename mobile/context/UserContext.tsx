
import { createContext, useContext, useEffect, useState } from "react";

import * as SecureStore from "expo-secure-store";

import { User } from "@/types/user";
import { UserContextType } from "@/types/UserContextType";
import { useAuth } from "./AuthContext";


  export const UserContext = createContext<UserContextType | undefined>(
    undefined,
  );
  
  export function UserProvider({ children }: { children: React.ReactNode }) {

    const [loading, setLoading] = useState(true);
    const { token } = useAuth()
    

    const getMe = async () => {
        setLoading(true);
        try {
            console.log("Getting me");

            const response = await fetch("http://192.168.8.223:8080/api/users/me", {
                method: "GET",
                headers: { "Content-Type": "application/json", 
                             Cookie: `token=${token}`,
                        },
            });

            console.log("User response: ", response);

            if (response.ok) {
                const data = await response.json();
                console.log ("user data",data)
                const user: User = {
                    id: data.id,
                    email: data.email,
                    firstName: data.firstName,
                    lastName: data.lastName,
                    age: data.age,
                    interests: data.interests,
                    phoneNumber: data.phoneNumber,
                };
                return user;
            } else {
                throw new Error("Error getting user")
            }

        } catch (error: any) {
                throw new Error(error)
        } finally {
            setLoading(false)
        }
    };
  
  
    return (
      <UserContext.Provider
        value={{ getMe, loading }}
      >
        {children}
      </UserContext.Provider>
    );
  }
  export const useUser = () => {
    const ctx = useContext(UserContext);
    if (!ctx) throw new Error("UseContext error");
    return ctx;
  };
  