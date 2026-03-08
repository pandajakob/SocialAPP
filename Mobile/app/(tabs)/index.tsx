import React, { useState } from 'react';
import { View, Text, FlatList, TextInput } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import PostCard from '../../components/PostCard';
import { Post } from '../../types/post';
import { MOCK_POSTS } from '@/constants/MockData';
import CategoryFilter from '@/components/CategoryFilter';

export default function ExploreScreen() {
 

  const [posts, setPosts] = useState<Post[]>(MOCK_POSTS); // Use the Model in your state

  return (
    <View className="flex-1 bg-gray-50 px-4 pt-14">
      <Text className="text-3xl font-bold mb-4">Explore</Text>

      {/* Search Header */}
      <View className="flex-row items-center bg-white rounded-2xl px-4 py-3 mb-6 border border-gray-200">
        <Ionicons name="search" size={20} color="#9CA3AF" />
        <TextInput placeholder="Search..." className="flex-1 ml-3" />
      </View>
      <CategoryFilter />
      <FlatList
        data={posts}
        renderItem={({ item }) => <PostCard post={item} />} // Pass data to the child
        keyExtractor={(item) => item.postId.toString()}
        contentContainerStyle={{ paddingBottom: 100 }}
      />
    </View>
  );
}

