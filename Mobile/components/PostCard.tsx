import { View, Text, Image, TouchableOpacity } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { router } from "expo-router";

import { Post } from "../types/post";
import { CATEGORY_EMOJIS } from "@/constants/categoryEmojis";

interface PostCardProps {
  post: Post;
}

export default function PostCard({ post }: PostCardProps) {
  const hasPhoto = !!post.photoUrl;

  const openPost = () => {
    router.push(`/post/${post.postId}`);
  };

  return (
    <TouchableOpacity
      onPress={openPost}
      activeOpacity={0.8}
      className={
        hasPhoto
          ? "bg-white rounded-3xl mb-5 overflow-hidden border border-gray-100 shadow-sm"
          : "bg-white rounded-3xl mb-3 overflow-hidden border border-gray-100 shadow-sm"
      }
    >
      {hasPhoto && (
        <Image source={{ uri: post.photoUrl }} className="w-full h-48" />
      )}

      <View className={hasPhoto ? "p-4" : "px-4 py-3"}>
        <View className="flex-row mb-2">
        {post.categories.map((cat) => (
          <View
            key={cat.id}
            className="flex-row items-center bg-white border border-gray-100 px-3 py-1.5 rounded-full mr-2"
          >
            <Text className="mr-1.5 text-sm">
              {CATEGORY_EMOJIS[cat.name.toLowerCase()] ?? "📌"}
            </Text>

            <Text className="text-gray-700 text-xs font-semibold">
              {cat.name}
            </Text>
          </View>
        ))}
      </View>

        <Text className="text-xl font-bold text-gray-900 mb-1">
          {post.title}
        </Text>

        <Text
          className={
            hasPhoto
              ? "text-gray-500 text-sm mb-3"
              : "text-gray-500 text-sm mb-2"
          }
          numberOfLines={hasPhoto ? 2 : 1}
        >
          {post.description ?? "No description"}
        </Text>

        <View
          className={
            hasPhoto
              ? "flex-row items-center justify-between border-t border-gray-50 pt-3"
              : "flex-row items-center justify-between border-t border-gray-50 pt-2"
          }
        >
          <View className="flex-row items-center">
            <Ionicons name="location-outline" size={16} color="#6B7280" />
            <Text className="text-gray-500 text-xs ml-1">
              {post.location.city}
            </Text>
          </View>

          <Text className="text-gray-500 text-xs">
            Ages: {post.ageFrom}-{post.ageTo}
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
}