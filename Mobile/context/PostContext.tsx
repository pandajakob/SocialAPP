
import { createContext, useContext, useEffect, useState } from "react";

import { useAuth } from "./AuthContext";
import { UserContextType } from "@/types/UserContextType";
import { API_BASE } from "@/constants/api";
import { Post } from "@/types/post";
import { PostContextType } from "@/types/PostContextType";


export const PostContext = createContext<PostContextType | undefined>(
  undefined,
);

export function PostProvider({ children }: { children: React.ReactNode }) {
  const [userPosts, setUserPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const { token } = useAuth();

  const getUserPosts = async (): Promise<Post[]> => {
    if (!token) {
      throw new Error("No auth token available");
    }

    setLoading(true);

    try {
      console.log(API_BASE + "/api/posts")
      const response = await fetch(`${API_BASE}/api/posts`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Cookie: `token=${token}`,
        },
      });
      console.log("response", response)

      if (!response.ok) {
        throw new Error("Error getting posts");
      }

      const data = await response.json();

      console.log("data", data)
      
      return data;
    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown user fetch error");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    let isMounted = true;

    const loadPost = async () => {
      if (!token) {
        if (isMounted) {
          setLoading(false);
        }
        return;
      }

      try {
        const fetchedPosts = await getUserPosts();
        if (isMounted) {
          setUserPosts(fetchedPosts);
        }
      } catch (error) {
        console.log("Error loading user data:", error);
      }
    };

    loadPost();

    return () => {
      isMounted = false;
    };
  }, [token]);

  return (
    <PostContext.Provider value={{ userPosts, loading }}>
      {children}
    </PostContext.Provider>
  );
}

export const usePost = () => {
  const ctx = useContext(PostContext);
  if (!ctx) throw new Error("usePost must be used inside PostProvider");
  return ctx;
};
  