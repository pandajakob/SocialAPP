import React, { useState } from "react";
import { ScrollView, TouchableOpacity, Text, View } from "react-native";
import { useCategory } from "@/context/CategoryContext";

import LoadingView from "./LoadingView";
import { CATEGORY_EMOJIS } from "@/constants/categoryEmojis";

export default function CategoryFilter() {
  const { mainCategories, loading } = useCategory();
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

  const toggleCategory = (categoryId: string) => {
    setSelectedCategories((current) =>
      current.includes(categoryId)
        ? current.filter((id) => id !== categoryId)
        : [...current, categoryId]
    );
  };

  if (loading) {
    return LoadingView();
  }

  return (
    <View className="mb-6">
      <ScrollView
        horizontal
        showsHorizontalScrollIndicator={false}
        contentContainerStyle={{ paddingHorizontal: 4 }}
      >
        {mainCategories.map((category) => {
          const isSelected = selectedCategories.includes(category.id.toString());

          return (
            <TouchableOpacity
              key={category.id}
              onPress={() => toggleCategory(category.id.toString())}
              activeOpacity={0.8}
              className={`flex-row items-center px-4 py-2 rounded-full mr-3 ${
                isSelected
                  ? "bg-blue-600 border border-blue-600"
                  : "bg-white border border-gray-100"
              }`}
            >
              <Text className="mr-2 text-base">
                {CATEGORY_EMOJIS[category.name.toLowerCase()] ?? "📌"}
              </Text>

              <Text
                className={`text-sm font-semibold ${
                  isSelected ? "text-white" : "text-gray-700"
                }`}
              >
                {category.name}
              </Text>

      
            </TouchableOpacity>
          );
        })}
      </ScrollView>
    </View>
  );
}