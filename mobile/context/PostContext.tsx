
import { createContext, useContext, useEffect, useState } from "react";

import { useAuth } from "./AuthContext";
import { UserContextType } from "@/types/UserContextType";
import { API_BASE } from "@/constants/api";
import { Post } from "@/types/post";
import { PostContextType } from "@/types/PostContextType";


export const PostContext = createContext<PostContextType | undefined>(undefined);

export function PostProvider({ children }: { children: React.ReactNode }) {
  const [userPosts, setUserPosts] = useState<Post[]>([]);
  const [feed, setFeed] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const { token } = useAuth();

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

      const responsePosts: Post[] = data.map((p: any) => ({
        postId: String(p.postId),
        title: p.title,
        description: p.description,
        location: p.location,
        date: p.date,
        ageFrom: p.ageFrom,
        ageTo: p.ageTo,
        categories: p.categories ?? [],
        photoUrl: p.photoUrl ?? "",
      }));
      
      return responsePosts;
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
      const response = await fetch(`${API_BASE}/api/posts/feed`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Cookie: `token=${token}`,
        },
      });
  

      if (!response.ok) {
        throw new Error("Failed to get feed: " + response.status + " " + response.statusText);
      }

      const data = await response.json();

      const responsePosts: Post[] = data.map((p: any) => ({
        postId: String(p.postId),
        title: p.title,
        description: p.description,
        location: p.location,
        date: p.date,
        ageFrom: p.ageFrom,
        ageTo: p.ageTo,
        categories: p.categories ?? [],
        photoUrl: p.photoUrl ?? "",
      }));
      
      return responsePosts;
    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown user fetch error");
    } finally {
      setLoading(false);
    }
  };

  const createPost = async (post: Omit<Post, "postId" | "date">): Promise<Post> => {
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

    const createdPost: Post = {
      postId: String(data.postId),
      title: data.title,
      description: data.description,
      location: data.location,
      date: data.date,
      ageFrom: data.ageFrom,
      ageTo: data.ageTo,
      categories: data.categories ?? [],
      photoUrl: data.photoUrl ?? "",
    };

    // Keep local state up to date immediately
    setUserPosts((current) => [createdPost, ...current]);
    setFeed((current) => [createdPost, ...current]);

    return createdPost;
  } catch (error: any) {
    throw new Error(error?.message ?? "Unknown create post error");
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
          setUserPosts(fetchedUserPosts)

          setFeed(fetchedFeed);
        }
      } catch (error) {
        console.log("Error loading user data:", error);
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
  