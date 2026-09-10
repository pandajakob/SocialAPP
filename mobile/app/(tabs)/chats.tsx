import React from "react";
import { View, Text, FlatList, TouchableOpacity } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useRouter } from "expo-router";
import ChatItem from "../../components/ChatItem";
import LoadingView from "@/components/LoadingView";
import { useChat } from "@/context/ChatContext";

export default function ChatsScreen() {
  const router = useRouter();
  const { chats, loading } = useChat();

  if (loading) {
    return LoadingView();
  }

  const handleChatPress = (id: string) => {
    router.navigate(`/chat/chat`);
  };

  return (
    <View className="flex-1 bg-white pt-16">
      {/* Header */}
      <View className="px-6 flex-row justify-between items-center mb-4">
        <Text className="text-2xl font-bold tracking-tight text-black">
          Messages
        </Text>
        <TouchableOpacity>
          <Ionicons name="create-outline" size={24} color="black" />
        </TouchableOpacity>
      </View>

      {chats.length === 0 ? (
        <View className="flex-1 items-center justify-center">
          <Ionicons name="chatbubble-outline" size={48} color="#D1D5DB" />
          <Text className="text-gray-400 text-base mt-4">No messages yet</Text>
        </View>
      ) : (
        <FlatList
          data={chats}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => (
            <ChatItem chat={item} onPress={handleChatPress} />
          )}
          showsVerticalScrollIndicator={false}
          contentContainerStyle={{ paddingBottom: 100 }}
        />
      )}
    </View>
  );
}
