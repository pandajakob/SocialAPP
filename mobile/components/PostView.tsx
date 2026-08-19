import React from "react";
import { View, Text, Image } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { Post } from "@/types/post";


interface PostViewProps {
  post: Post;
}

export default function PostView({ post }: PostViewProps) {
  return (
    <View className="flex-1 bg-gray-50">
      {post.photoUrl ? (
        <Image
          source={{ uri: post.photoUrl }}
          className="w-full h-64"
          resizeMode="cover"
        />
      ) : (
        <View className="w-full h-64 bg-gray-200 items-center justify-center">
          <Ionicons name="image-outline" size={48} color="#9CA3AF" />
        </View>
      )}

      <View className="px-5 py-5">
        {/* Title */}
        <Text className="text-3xl font-bold mb-2">
          {post.title}
        </Text>

        {/* Categories */}
        <View className="flex-row flex-wrap mb-4">
          {post.categories.map((category) => (
            <View
              key={category.id}
              className="bg-black rounded-full px-3 py-1 mr-2 mb-2"
            >
              <Text className="text-white font-medium">
                {category.name}
              </Text>
            </View>
          ))}
        </View>

        {/* Description */}
        <Text className="text-base text-gray-700 leading-6 mb-5">
          {post.description}
        </Text>

        {/* Age */}
        <View className="flex-row items-center mb-4">
          <Ionicons name="people-outline" size={20} color="#6B7280" />
          <Text className="text-gray-700 ml-2">
            Ages {post.ageFrom}–{post.ageTo}
          </Text>
        </View>

        {/* Location */}
        <View className="flex-row items-start mb-4">
          <Ionicons name="location-outline" size={20} color="#6B7280" />
          <View className="ml-2 flex-1">
            <Text className="text-gray-700 font-medium">
              {post.location.city}, {post.location.country}
            </Text>

            <Text className="text-gray-500 mt-1">
              {post.location.formattedAddress}
            </Text>
          </View>
        </View>

        {/* Date */}
        <View className="flex-row items-center">
          <Ionicons name="calendar-outline" size={20} color="#6B7280" />
          <Text className="text-gray-500 ml-2">
            {new Date(post.date).toLocaleDateString()}
          </Text>
        </View>
      </View>
    </View>
  );
}