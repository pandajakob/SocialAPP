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
  const visibleCategories = post.categories.slice(0, 2);
  const hasMoreCategories = post.categories.length > 3;

  const openPost = () => {
    router.push(`/post/${post.postId}`);
  };

  return (
    <TouchableOpacity
      onPress={openPost}
      activeOpacity={0.85}
      className="bg-white rounded-3xl mb-4 overflow-hidden border border-gray-100 shadow-sm"
    >
      {hasPhoto && (
        <Image
          source={{ uri: post.photoUrl }}
          className="w-full h-48"
          resizeMode="cover"
        />
      )}

      <View className="p-4">
        {/* Header */}
        <View className="flex-row items-center justify-between mb-4">
          <View className="flex-row items-center flex-1">
            <View className="w-10 h-10 rounded-full bg-gray-100 items-center justify-center">
              <Ionicons name="person-outline" size={19} color="#6B7280" />
            </View>

            <View className="ml-2.5">
              <Text className="text-gray-900 text-sm font-semibold">
                {post.user.firstName}, {post.user.age}
              </Text>

              <View className="flex-row items-center mt-0.5">
                <Ionicons name="location-outline" size={12} color="#9CA3AF" />
                <Text className="text-gray-400 text-xs ml-1">
                  {post.location.city}
                </Text>
              </View>
            </View>
          </View>

          {/* Categories */}
          <View className="flex-row items-center justify-end ml-3 flex-shrink">
            {visibleCategories.map((category) => (
              <View
                key={category.id}
                className="flex-row items-center bg-gray-50 px-2.5 py-2 rounded-full ml-1.5"
              >
                <Text className="text-sm">
                  {CATEGORY_EMOJIS[category.name.toLowerCase()] ?? "📌"}
                </Text>

                <Text
                  className="text-gray-600 text-xs font-semibold ml-1"
                  numberOfLines={1}
                >
                  {category.name}
                </Text>
              </View>
            ))}

            {hasMoreCategories && (
              <View className="bg-gray-100 px-2.5 py-2 rounded-full ml-1.5">
                <Text className="text-gray-500 text-xs font-semibold">
                  +{post.categories.length - 3}
                </Text>
              </View>
            )}
          </View>
        </View>

        {/* Content */}
        <Text className="text-xl font-bold text-gray-900 mb-1">
          {post.title}
        </Text>

        <Text
          className="text-gray-500 text-sm leading-5 mb-4"
          numberOfLines={hasPhoto ? 2 : 3}
        >
          {post.description ?? "No description"}
        </Text>

        {/* Footer */}
        <View className="flex-row items-center justify-between border-t border-gray-100 pt-3">
          <Text className="text-gray-400 text-xs">
            Looking for ages {post.ageFrom}–{post.ageTo}
          </Text>

          <Ionicons name="chevron-forward" size={15} color="#D1D5DB" />
        </View>
      </View>
    </TouchableOpacity>
  );
}