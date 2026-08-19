import React from "react";
import { View, ActivityIndicator, Text } from "react-native";
import { useLocalSearchParams } from "expo-router";
import { usePost } from "@/context/PostContext";
import PostView from "@/components/PostView";

export default function PostScreen() {
  const { postId } = useLocalSearchParams<{ postId: string }>();
  const { feed, loading } = usePost();

  if (loading || !feed) {
    return (
      <View className="flex-1 items-center justify-center bg-white">
        <ActivityIndicator size="large" color="#000" />
      </View>
    );
  }

  const post = feed.find((post) => post.postId === postId);

  if (!post) {
    return (
      <View className="flex-1 items-center justify-center bg-white">
        <Text>Post not found</Text>
      </View>
    );
  }

  return <PostView post={post} />;
}