import React from "react";
import { View, Text, FlatList, TouchableOpacity } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import ChatItem from "../../components/ChatItem";
import { MOCK_CHATS } from "../../constants/MockData"; // Move your mock data here!
import { useRouter } from "expo-router";

export default function ChatsScreen() {
    const router = useRouter();

  const handleChatPress = (id: number) => {
    router.navigate(`/chat/chat`)
  };

  return (
    <View className="flex-1 bg-white pt-16">
      {/* Header */}

      <View className="px-6 flex-row justify-between items-center mb-4">
        <Text className="text-2xl font-bold tracking-tight text-black">Messages</Text>


        <TouchableOpacity>
          <Ionicons name="create-outline" size={24} color="black" />
        </TouchableOpacity>
      </View>

      {/* List */}
      <FlatList
        data={MOCK_CHATS}
        keyExtractor={(item) => item.chatId.toString()}
        renderItem={({ item }) => (
          <ChatItem chat={item} onPress={handleChatPress} />
        )}
        showsVerticalScrollIndicator={false}
      />
    </View>
  );
}