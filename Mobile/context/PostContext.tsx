import React from "react";
import { createContext, useContext, useEffect, useState } from "react";
import { useAuth } from "./AuthContext";
import { API_BASE } from "@/constants/api";
import { Post } from "@/types/post";
import { PostContextType } from "@/types/PostContextType";
import * as ExpoLocation from "expo-location";
import { Location } from "@/types/location";


export const PostContext = createContext<PostContextType | undefined>(undefined);

export function PostProvider({ children }: { children: React.ReactNode }) {
  const [userPosts, setUserPosts] = useState<Post[]>([]);
  const [feed, setFeed] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const { token } = useAuth();

  const getUserLocation = async () => {
      const { status } = await ExpoLocation.requestForegroundPermissionsAsync();

      if (status !== "granted") {
        throw new Error("Location permission denied");
      }
      return await ExpoLocation.getCurrentPositionAsync({});
  }

  const getUserPosts = async (): Promise<Post[]> => {
    if (!token) {
      throw new Error("No auth token available");
    }

    setLoading(true);
    try {
      const response = await fetch(`${API_BASE}/api/posts`, {
        method: "GET",

        headers: {
          "Content-Type": "application/json",
          Cookie: `token=${token}`,
        },
      });
    
      if (!response.ok) {
        throw new Error("Failed to get userPosts: " + response.status + " " + response.statusText);
      }

      const data = await response.json();

      return data as Post[];
      
    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown user fetch error");
    } finally {
      setLoading(false);
    }
  };


  const getFeed = async (): Promise<Post[]> => {
    if (!token) {
      throw new Error("No auth token available");
    }
    setLoading(true);
   
    try {
      let reactUserLocation = await getUserLocation()
      
      const location: Location = {
            latitude: reactUserLocation.coords.latitude,
            longitude: reactUserLocation.coords.longitude,
            city: "",
            country: "",
            formattedAddress: "",
          } 

          const response = await fetch(`${API_BASE}/api/posts/feed`, {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Cookie: `token=${token}`,
              },
              body: JSON.stringify(location)
          
          });
      
          if (!response.ok) {
            throw new Error("Failed to get feed: " + response.status + " " + response.statusText);
          }

          const data = await response.json();
          return data as Post[]
      

    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown user fetch error");
    } finally {
      setLoading(false);
    }
  };

  const createPost = async (post: Omit<Post, "postId" | "date">): Promise<Post> => {
    setLoading(true)
    if (!token) {
      throw new Error("No auth token available");
    }

    try {
      const response = await fetch(`${API_BASE}/api/posts`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Cookie: `token=${token}`,
        },
        body: JSON.stringify(post),
      });

      if (!response.ok) {
        const errorText = await response.text();

        throw new Error(
          `Failed to create post: ${response.status} ${errorText}`
        );
      }

      const data = await response.json();

      const createdPost = data as Post
      
      // Keep local state up to date immediately
      setUserPosts((current) => [createdPost, ...current]);
      setFeed((current) => [createdPost, ...current]);

      return createdPost;
    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown create post error");
    } finally {
      setLoading(false)
    }
  };
  
  useEffect(() => {
    let isMounted = true;

    const loadPosts = async () => {
      if (!token) {
        if (isMounted) {
          setLoading(false);
        }
        return;
      }

      try {
        const fetchedUserPosts = await getUserPosts();
        const fetchedFeed = await getFeed();

        if (isMounted) {
          setUserPosts(fetchedUserPosts);

          setFeed(fetchedFeed);
        }
      } catch (error) {
        console.log("Error loading posts:", error);
      }
    };
    
    loadPosts();

    return () => {
      isMounted = false;
    };
  }, [token]);

  return (
    <PostContext.Provider value={{ userPosts, feed, loading, createPost }}>
      {children}
    </PostContext.Provider>
  );
}

export const usePost = () => {
  const ctx = useContext(PostContext);
  if (!ctx) throw new Error("usePost must be used inside PostProvider");
  return ctx;
};
  