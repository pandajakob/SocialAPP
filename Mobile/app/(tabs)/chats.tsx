import { Pressable, ScrollView, Text } from "react-native";
import "../../global.css";

export default function Chats() {
  const mockChats = [
    { id: 1, name: "Alice", lastMessage: "Hey, how are you?" },
    { id: 2, name: "Bob", lastMessage: "Let's catch up tomorrow." },
    { id: 3, name: "Charlie", lastMessage: "Got the files. Thanks!" },
  ];

  return (
    <ScrollView className="flex-1 bg-gray-100 px-6 pt-24">
      <Text className="text-3xl font-bold text-gray-800 mb-6">Chats</Text>

      {mockChats.map((chat) => (
        <Pressable
          key={chat.id}
          className="bg-white rounded-2xl p-4 mb-4 shadow-md"
          onPress={() => console.log(`Tapped chat with ${chat.name}`)}
        >
          <Text className="text-lg font-semibold text-gray-800">
            {chat.name}
          </Text>
          <Text className="text-gray-500 mt-1">{chat.lastMessage}</Text>
        </Pressable>
      ))}
    </ScrollView>
  );
}
