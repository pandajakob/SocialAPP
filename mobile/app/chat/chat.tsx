import React, { useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  TextInput,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
} from "react-native";
import { useLocalSearchParams, useRouter } from "expo-router";
import { Ionicons } from "@expo/vector-icons";
import { usePost } from "@/context/PostContext";
import { CATEGORY_EMOJIS } from "@/constants/categoryEmojis";

export default function ChatScreen() {
  const { postId } = useLocalSearchParams<{ postId: string }>();
  const { feed, userPosts } = usePost();
  const router = useRouter();
  const [message, setMessage] = useState("");

  const post =
    feed.find((p) => p.id === postId) ??
    userPosts.find((p) => p.id === postId);

  return (
    <KeyboardAvoidingView
      className="flex-1 bg-white"
      behavior={Platform.OS === "ios" ? "padding" : "height"}
    >
      {/* Header */}
      <View className="pt-14 pb-4 px-5 flex-row items-center border-b border-gray-100">
        <TouchableOpacity onPress={() => router.back()} className="mr-3">
          <Ionicons name="chevron-back" size={26} color="#111827" />
        </TouchableOpacity>

        <View className="flex-1">
          <Text
            className="text-base font-bold text-gray-900"
            numberOfLines={1}
          >
            {post ? `${post.user.firstName} ${post.user.lastName}` : "Chat"}
          </Text>
          {post && (
            <Text className="text-xs text-gray-400 mt-0.5" numberOfLines={1}>
              {post.title}
            </Text>
          )}
        </View>
      </View>

      {/* Post context card */}
      {post && (
        <View className="mx-5 mt-4 bg-gray-50 rounded-2xl border border-gray-100 p-4 flex-row items-center">
          <View className="w-9 h-9 rounded-full bg-white border border-gray-100 items-center justify-center mr-3">
            {post.categories[0] ? (
              <Text className="text-base">
                {CATEGORY_EMOJIS[post.categories[0].name.toLowerCase()] ??
                  "📌"}
              </Text>
            ) : (
              <Ionicons name="document-outline" size={18} color="#6B7280" />
            )}
          </View>

          <View className="flex-1">
            <Text className="text-xs text-gray-400 font-semibold uppercase tracking-widest">
              About post
            </Text>
            <Text
              className="text-gray-900 font-semibold text-sm mt-0.5"
              numberOfLines={1}
            >
              {post.title}
            </Text>
          </View>
        </View>
      )}

      {/* Messages area — empty state */}
      <ScrollView
        className="flex-1 px-5"
        contentContainerStyle={{
          flexGrow: 1,
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Ionicons name="chatbubble-ellipses-outline" size={44} color="#D1D5DB" />
        <Text className="text-gray-400 text-sm mt-3 text-center">
          No messages yet.{"\n"}Send the first one!
        </Text>
      </ScrollView>

      {/* Input bar */}
      <View className="px-5 py-4 border-t border-gray-100 flex-row items-end">
        <TextInput
          value={message}
          onChangeText={setMessage}
          placeholder="Type a message..."
          placeholderTextColor="#9CA3AF"
          className="flex-1 bg-gray-50 rounded-2xl px-4 py-3 text-gray-900 border border-gray-100 mr-3"
          multiline
        />
        <TouchableOpacity
          className="w-11 h-11 rounded-full bg-gray-900 items-center justify-center opacity-40"
          disabled
        >
          <Ionicons name="arrow-up" size={18} color="#fff" />
        </TouchableOpacity>
      </View>
    </KeyboardAvoidingView>
  );
}
