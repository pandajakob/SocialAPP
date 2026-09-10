import React from "react";
import { createContext, useContext, useEffect, useState } from "react";
import { useAuth } from "./AuthContext";
import { API_BASE } from "@/constants/api";
import { Chat } from "@/types/chat";
import { ChatContextType } from "@/types/ChatContextType";

export const ChatContext = createContext<ChatContextType | undefined>(undefined);

export function ChatProvider({ children }: { children: React.ReactNode }) {
  const [chats, setChats] = useState<Chat[]>([]);
  const [loading, setLoading] = useState(true);
  const { token } = useAuth();

  const getAllChats = async (): Promise<Chat[]> => {
    if (!token) {
      throw new Error("No auth token available");
    }

    setLoading(true);

    try {
      const response = await fetch(`${API_BASE}/api/chats`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Cookie: `token=${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(
          `Failed to get chats: ${response.status} ${response.statusText}`
        );
      }

      const data = await response.json();

      return data as Chat[];
    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown chat fetch error");
    } finally {
      setLoading(false);
    }
  };

  const createChat = async (
    postId: string,
    messageContent: string
  ): Promise<Chat> => {
    if (!token) {
      throw new Error("No auth token available");
    }

    setLoading(true);

    try {
      const response = await fetch(`${API_BASE}/api/chats`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Cookie: `token=${token}`,
        },
        body: JSON.stringify({ postId, messageContent }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Failed to create chat: ${response.status} ${errorText}`);
      }

      const data = await response.json();
      const createdChat = data as Chat;

      setChats((current) => [createdChat, ...current]);

      return createdChat;
    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown create chat error");
    } finally {
      setLoading(false);
    }
  };

  const sendMessage = async (
    chatId: string,
    messageContent: string
  ): Promise<Chat> => {
    if (!token) {
      throw new Error("No auth token available");
    }

    try {
      const response = await fetch(`${API_BASE}/api/chats/message`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Cookie: `token=${token}`,
        },
        body: JSON.stringify({ chatId, messageContent }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Failed to send message: ${response.status} ${errorText}`);
      }

      const data = await response.json();
      const updatedChat = data as Chat;

      setChats((current) =>
        current.map((c) => (c.id === updatedChat.id ? updatedChat : c))
      );

      return updatedChat;
    } catch (error: any) {
      throw new Error(error?.message ?? "Unknown send message error");
    }
  };

  useEffect(() => {
    let isMounted = true;

    const loadChats = async () => {
      if (!token) {
        if (isMounted) {
          setLoading(false);
        }
        return;
      }

      try {
        const fetchedChats = await getAllChats();
        if (isMounted) {
          setChats(fetchedChats);
        }
      } catch (error) {
        console.log("Error loading chats:", error);
      }
    };

    loadChats();

    return () => {
      isMounted = false;
    };
  }, [token]);

  return (
    <ChatContext.Provider value={{ chats, loading, createChat, sendMessage }}>
      {children}
    </ChatContext.Provider>
  );
}

export const useChat = () => {
  const ctx = useContext(ChatContext);
  if (!ctx) throw new Error("useChat must be used inside ChatProvider");
  return ctx;
};
