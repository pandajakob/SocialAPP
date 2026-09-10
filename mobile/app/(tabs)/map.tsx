import React from "react";
import { View, ActivityIndicator } from "react-native";
import MapView, { Marker } from "react-native-maps";
import { router } from "expo-router";
import { usePost } from "@/context/PostContext";
import LoadingView from "@/components/LoadingView";

export default function MapScreen() {
  const { feed, loading } = usePost();

  if (loading || !feed) {
    return LoadingView
  }

  return (
    <View className="flex-1">
      <MapView
        style={{ flex: 1 }}
        initialRegion={{
          latitude: 55.6761,
          longitude: 12.5683,
          latitudeDelta: 0.5,
          longitudeDelta: 0.5,
        }}
        showsUserLocation
      >
        {feed.map((post) => {
          if (!post.location) return null;

          return (
            <Marker
              key={post.id}
              coordinate={{
                latitude: post.location.latitude,
                longitude: post.location.longitude,
              }}
              pinColor="black"
              title={post.title}
              description={post.description}
              onCalloutPress={() => {
                router.push(`/post/${post.id}`);
              }}
            />
          );
        })}
      </MapView>
    </View>
  );
}