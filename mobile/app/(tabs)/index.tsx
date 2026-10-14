import React, { useEffect, useRef } from "react";
import Animated, { FadeIn } from "react-native-reanimated";
import { View, Text, FlatList, TextInput, ActivityIndicator, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import AnimatedPostCard from '@/components/AnimatedPostCard';
import CategoryFilter from '@/components/CategoryFilter';
import { usePost } from '@/context/PostContext';
import LoadingView from '@/components/LoadingView';
import { useFilteredFeed } from '@/hooks/use-filtered-feed';


export default function ExploreScreen() {

const { feed, loading } = usePost();
const { filteredPosts, resolving } = useFilteredFeed(feed ?? []);
const seenIds = useRef(new Set<string>());

// Forget posts that left the list so they animate in again when they return
useEffect(() => {
  const currentIds = new Set(filteredPosts.map((post) => post.id));
  seenIds.current.forEach((id) => {
    if (!currentIds.has(id)) seenIds.current.delete(id);
  });
}, [filteredPosts]);

  if (loading || !feed) {
    return LoadingView()
  }

  return (
    <View className="flex-1 bg-gray-50 px-4 pt-14">
      <View className="flex-row justify-between items-center mb-4">
        <Text className="text-2xl font-bold tracking-tight text-black">
          Explore
        </Text>
      </View>

      {/* Search Header */}
      <View className="flex-row items-center bg-white rounded-2xl px-4 py-3 mb-6 border border-gray-200">
        <Ionicons name="search" size={20} color="#9CA3AF" />
        <TextInput placeholder="Search..." className="flex-1 ml-3" />
      </View>
      <CategoryFilter />

      {feed ?
      <FlatList
        data={filteredPosts}
        renderItem={({ item }) => (
          <AnimatedPostCard post={item} seenIds={seenIds.current} />
        )}
        keyExtractor={(item) => item.id}
        contentContainerStyle={{ paddingBottom: 100 }}
        ListEmptyComponent={
          <Animated.View entering={FadeIn.duration(250)}>
            {resolving ? (
              <ActivityIndicator className="mt-8" />
            ) : (
              <Text className="text-center text-gray-500 mt-8">
                No posts in the selected categories
              </Text>
            )}
          </Animated.View>
        }
      /> : 

      <Text className="text-3xl font-bold mb-4">Error loading feed</Text>
      }

      </View>
  );
}

