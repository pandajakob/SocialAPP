import React from 'react';
import { ScrollView, TouchableOpacity, Text, View } from 'react-native';

// Standardized categories matching your UML 'Category' entity
const CATEGORIES = [
  { id: 1, name: 'Sports', emoji: '🏀' },
  { id: 2, name: 'Gaming', emoji: '🎮' },
  { id: 3, name: 'Social', emoji: '🍻' },
  { id: 4, name: 'Creative', emoji: '🎨' },
  { id: 5, name: 'Music', emoji: '🎸' },
];

export default function CategoryFilter() {
  return (
    <View className="mb-6">
      <ScrollView
        horizontal
        showsHorizontalScrollIndicator={false}
        contentContainerStyle={{ paddingHorizontal: 4 }}
      >
        {CATEGORIES.map((cat) => (
          <TouchableOpacity
            key={cat.id}
            onPress={() => console.log(`Filtered by ${cat.name}`)}
            className="flex-row items-center bg-white border border-gray-100 px-4 py-2 rounded-full mr-3 active:bg-blue-50"
          >
            <Text className="mr-2 text-base">{cat.emoji}</Text>
            <Text className="text-sm font-semibold text-gray-700">
              {cat.name}
            </Text>
          </TouchableOpacity>
        ))}
      </ScrollView>
    </View>
  );
}