import React from "react";
import { View, Text, Image, ScrollView } from "react-native";
import { Ionicons } from "@expo/vector-icons";

import { Post } from "@/types/post";
import { CATEGORY_EMOJIS } from "@/constants/categoryEmojis";

interface PostViewProps {
  post: Post;
}

export default function PostView({ post }: PostViewProps) {
  return (
    <ScrollView
      className="flex-1 bg-gray-50"
      showsVerticalScrollIndicator={false}
    >
      {/* Image */}
      {post.photoUrl ? (
        <Image
          source={{ uri: post.photoUrl }}
          className="w-full h-72"
          resizeMode="cover"
        />
      ) : (
        <View className="w-full h-72 bg-gray-200 items-center justify-center">
          <Ionicons name="image-outline" size={48} color="#9CA3AF" />
        </View>
      )}

      <View className="px-5 pt-5 pb-8">
        {/* Author */}
        <View className="flex-row items-center mb-5">
          <View className="w-11 h-11 rounded-full bg-gray-100 items-center justify-center">
            <Ionicons name="person-outline" size={21} color="#6B7280" />
          </View>

          <View className="ml-3">
            <Text className="text-gray-900 font-semibold text-base">
              {post.user.firstName}, {post.user.age}
            </Text>

            <View className="flex-row items-center mt-0.5">
              <Ionicons
                name="location-outline"
                size={13}
                color="#9CA3AF"
              />
              <Text className="text-gray-400 text-xs ml-1">
                {post.location.city}
              </Text>
            </View>
          </View>
        </View>

        {/* Title */}
        <Text className="text-3xl font-bold text-gray-900 leading-9 mb-3">
          {post.title}
        </Text>

        {/* Categories */}
        <View className="flex-row flex-wrap mb-5">
          {post.categories.map((category) => (
            <View
              key={category.id}
              className="flex-row items-center bg-gray-100 px-3 py-1.5 rounded-full mr-2 mb-2"
            >
              <Text className="text-sm mr-1.5">
                {CATEGORY_EMOJIS[category.name.toLowerCase()] ?? "📌"}
              </Text>

              <Text className="text-gray-700 text-xs font-semibold">
                {category.name}
              </Text>
            </View>
          ))}
        </View>

        {/* Description */}
        <Text className="text-gray-700 text-base leading-6 mb-6">
          {post.description ?? "No description"}
        </Text>

        {/* Matching info */}
        <View className="bg-white rounded-2xl border border-gray-100 p-4 mb-5">
          <Text className="text-gray-900 font-semibold mb-3">
            Looking for
          </Text>

          <View className="flex-row items-center">
            <View className="w-9 h-9 rounded-full bg-gray-50 items-center justify-center">
              <Ionicons
                name="people-outline"
                size={18}
                color="#6B7280"
              />
            </View>

            <View className="ml-3">
              <Text className="text-gray-900 font-medium">
                Ages {post.ageFrom}–{post.ageTo}
              </Text>
              <Text className="text-gray-400 text-xs mt-0.5">
                Preferred age range
              </Text>
            </View>
          </View>
        </View>

        {/* Additional details */}
        <View className="bg-white rounded-2xl border border-gray-100 overflow-hidden">
          {/* Location */}
          <View className="flex-row items-start p-4">
            <View className="w-9 h-9 rounded-full bg-gray-50 items-center justify-center">
              <Ionicons
                name="location-outline"
                size={18}
                color="#6B7280"
              />
            </View>

            <View className="ml-3 flex-1">
              <Text className="text-gray-900 font-medium">
                {post.location.city}, {post.location.country}
              </Text>

              <Text className="text-gray-400 text-sm mt-1">
                {post.location.formattedAddress}
              </Text>
            </View>
          </View>

          <View className="h-px bg-gray-100 ml-16" />

          {/* Date */}
          <View className="flex-row items-center p-4">
            <View className="w-9 h-9 rounded-full bg-gray-50 items-center justify-center">
              <Ionicons
                name="calendar-outline"
                size={18}
                color="#6B7280"
              />
            </View>

            <View className="ml-3">
              <Text className="text-gray-900 font-medium">
                {new Date(post.date).toLocaleDateString()}
              </Text>
              <Text className="text-gray-400 text-xs mt-0.5">
                Posted date
              </Text>
            </View>
          </View>
        </View>
      </View>
    </ScrollView>
  );
}