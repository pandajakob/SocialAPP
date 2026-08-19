import React from "react";
import { View, Text, Image, Pressable } from "react-native";
import { Chat } from "../types/chat";

interface ChatItemProps {
  chat: Chat;
  onPress: (id: number) => void;
}

export default function ChatItem({ chat, onPress }: ChatItemProps) {
  // Determine the 'other' participant to show their name/photo
  const otherUser = chat.participants[0]; 

  return (
    <Pressable 
      className="flex-row items-center px-6 py-4 active:bg-gray-50 border-b border-gray-50"
      onPress={() => onPress(chat.chatId)}
    >
      {/* User Profile Photo */}
      <Image 
        source={{ uri: otherUser.profilePhoto.url }} 
        className="w-14 h-14 rounded-full mr-4 border border-gray-200" 
      />

      <View className="flex-1">
        <View className="flex-row justify-between items-baseline">
          {/* Post Title acts as the Context Header */}
          <Text className="text-s font-bold uppercase tracking-widest mb-1">
            {chat.post.title}
          </Text>
          <Text className="text-gray-400 text-xs">{chat.time}</Text>
        </View>

   
        
        <Text className="text-gray-500 text-sm mt-0.5" numberOfLines={1}>
          {chat.lastMessage}
        </Text>
      </View>
    </Pressable>
  );
}