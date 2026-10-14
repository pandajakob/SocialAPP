import React from "react";
import { ScrollView, TouchableOpacity, Text, View } from "react-native";
import { useCategory } from "@/context/CategoryContext";
import LoadingView from "./LoadingView";


export default function CategoryFilter() {
  const { mainCategories, loading, selectedCategoryIds, toggleCategory } =
    useCategory();

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
          const isSelected = selectedCategoryIds.includes(category.id);

          return (
            <TouchableOpacity
              key={category.id}
              onPress={() => toggleCategory(category.id)}
              activeOpacity={0.8}
              className={`flex-row items-center px-4 py-2 rounded-full mr-3 ${
                isSelected
                  ? "bg-blue-600 border border-blue-600"
                  : "bg-white border border-gray-100"
              }`}
            >
              <Text className="mr-2 text-base">
                {category.emoji}
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