import React from "react";
import { ScrollView, TouchableOpacity, Text, View } from "react-native";
import { useCategory } from "@/context/CategoryContext";

import LoadingView from "./LoadingView";
import { CATEGORY_EMOJIS } from "@/constants/categoryEmojis";

export default function CategoryFilter() {
  const { mainCategories, loading } = useCategory();

  if (loading) {
    return   LoadingView()
  }
  return (
    <View className="mb-6">
      <ScrollView
        horizontal
        showsHorizontalScrollIndicator={false}
        contentContainerStyle={{ paddingHorizontal: 4 }}
      >
        {mainCategories.map((category) => (
          <TouchableOpacity
            key={category.id}
            onPress={() => console.log(`Filtered by ${category.name}`)}
            className="flex-row items-center bg-white border border-gray-100 px-4 py-2 rounded-full mr-3 active:bg-blue-50"
          >
            <Text className="mr-2 text-base">
              {CATEGORY_EMOJIS[category.name.toLowerCase()] ?? "📌"}
            </Text>

            <Text className="text-sm font-semibold text-gray-700">
              {category.name}
            </Text>
          </TouchableOpacity>
        ))}
      </ScrollView>
    </View>
  );
}