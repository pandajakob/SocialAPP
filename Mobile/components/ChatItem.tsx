import React from "react";
import { View, Text, Image, Pressable } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { Chat } from "../types/chat";

interface ChatItemProps {
  chat: Chat;
  onPress: (id: string) => void;
}

function formatDate(dateString: string): string {
  const date = new Date(dateString);
  const now = new Date();
  const diffMs = now.getTime() - date.getTime();
  const diffMins = Math.floor(diffMs / 60000);
  const diffHours = Math.floor(diffMs / 3600000);
  const diffDays = Math.floor(diffMs / 86400000);

  if (diffMins < 1) return "now";
  if (diffMins < 60) return `${diffMins}m`;
  if (diffHours < 24) return `${diffHours}h`;
  if (diffDays < 7) return `${diffDays}d`;
  return date.toLocaleDateString("en-US", { month: "short", day: "numeric" });
}

export default function ChatItem({ chat, onPress }: ChatItemProps) {
  const otherUser = chat.participants[0];
  const lastMessage = chat.messages[chat.messages.length - 1];
  const isRead = lastMessage?.state === "READ";

  return (
    <Pressable
      className="flex-row items-center px-6 py-4 active:bg-gray-50 border-b border-gray-50"
      onPress={() => onPress(chat.id)}
    >
      {/* Avatar */}
      {otherUser?.photoUrl ? (
        <Image
          source={{ uri: otherUser.photoUrl }}
          className="w-14 h-14 rounded-full mr-4 border border-gray-100"
        />
      ) : (
        <View className="w-14 h-14 rounded-full bg-gray-100 items-center justify-center mr-4">
          <Ionicons name="person-outline" size={24} color="#6B7280" />
        </View>
      )}

      <View className="flex-1">
        <View className="flex-row justify-between items-baseline mb-1">
          <Text className="text-sm font-bold text-gray-900">
            {otherUser
              ? `${otherUser.firstName} ${otherUser.lastName}`
              : "Unknown"}
          </Text>
          {lastMessage && (
            <Text className="text-gray-400 text-xs">
              {formatDate(chat.date)}
            </Text>
          )}
        </View>

        <Text
          className="text-xs font-semibold text-gray-400 uppercase tracking-widest mb-1"
          numberOfLines={1}
        >
          {chat.post.title}
        </Text>

        <View className="flex-row items-center justify-between">
          <Text
            className={`text-sm flex-1 mr-2 ${isRead ? "text-gray-400" : "text-gray-700 font-medium"}`}
            numberOfLines={1}
          >
            {lastMessage?.content ?? "No messages yet"}
          </Text>

          {!isRead && lastMessage && (
            <View className="w-2 h-2 rounded-full bg-blue-600" />
          )}
        </View>
      </View>
    </Pressable>
  );
}
