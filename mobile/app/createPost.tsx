import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  ScrollView,
  Pressable,
  ActivityIndicator,
  Alert,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { router } from "expo-router";
import MapView, { Marker } from "react-native-maps";
import * as Location from "expo-location";
import { usePost } from "@/context/PostContext";
import { useCategory } from "@/context/CategoryContext";

const MAX_TITLE_LENGTH = 100;

export default function CreatePostScreen() {
  const { createPost } = usePost();
  const { categories } = useCategory();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [ageFrom, setAgeFrom] = useState("");
  const [ageTo, setAgeTo] = useState("");
  const [photoURL, setPhotoURL] = useState("");

  const [selectedLocation, setSelectedLocation] = useState<{
    latitude: number;
    longitude: number;
  } | null>(null);

  const [selectedCategories, setSelectedCategories] = useState<number[]>([]);
  const [submitting, setSubmitting] = useState(false);
  const [gettingLocation, setGettingLocation] = useState(false);

  const toggleCategory = (id: number) => {
    setSelectedCategories((current) =>
      current.includes(id)
        ? current.filter((categoryId) => categoryId !== id)
        : [...current, id]
    );
  };

  const getCurrentLocation = async () => {
    try {
      setGettingLocation(true);

      const { status } =
        await Location.requestForegroundPermissionsAsync();

      if (status !== "granted") {
        Alert.alert(
          "Location permission",
          "Please allow location access to use your current location."
        );
        return;
      }

      const location = await Location.getCurrentPositionAsync({});

      setSelectedLocation({
        latitude: location.coords.latitude,
        longitude: location.coords.longitude,
      });
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Could not get your current location.");
    } finally {
      setGettingLocation(false);
    }
  };

  const handleCreatePost = async () => {
    const trimmedTitle = title.trim();
    const trimmedDescription = description.trim();

    if (!trimmedTitle) {
      Alert.alert("Missing title", "Please enter a title.");
      return;
    }

    if (trimmedTitle.length > MAX_TITLE_LENGTH) {
      Alert.alert(
        "Title too long",
        `Your title can be up to ${MAX_TITLE_LENGTH} characters.`
      );
      return;
    }

    if (!trimmedDescription) {
      Alert.alert("Missing description", "Please enter a description.");
      return;
    }

    if (selectedCategories.length === 0) {
      Alert.alert("Missing category", "Please select at least one category.");
      return;
    }

    if (!selectedLocation) {
      Alert.alert("Missing location", "Please select a location on the map.");
      return;
    }

    try {
      setSubmitting(true);

      const post = {
        title: trimmedTitle,
        description: trimmedDescription,
        location: {
          latitude: selectedLocation.latitude,
          longitude: selectedLocation.longitude,
        },
        categories: categories.filter((category) =>
          selectedCategories.includes(category.id)
        ),
        ageFrom: Number(ageFrom),
        ageTo: Number(ageTo),
        photoURL: photoURL.trim(),
      };

      await createPost(post);

      router.back();
      router.push("/profile");
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Could not create post.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <View className="flex-1 bg-gray-50">
      <ScrollView
        className="flex-1 px-4 pt-14"
        contentContainerStyle={{ paddingBottom: 120 }}
        keyboardShouldPersistTaps="handled"
      >
        {/* Header */}
        <View className="mb-6">
          <Text className="text-3xl font-bold">Create Post</Text>
          <Text className="text-gray-500 mt-1">
            Share something with the community
          </Text>
        </View>

        {/* Title */}
        <View className="flex-row items-center justify-between mb-2">
          <Text className="text-sm font-semibold text-gray-700">
            Title
          </Text>

          <Text
            className={`text-xs ${
              title.length >= MAX_TITLE_LENGTH
                ? "text-red-500"
                : "text-gray-400"
            }`}
          >
            {title.length}/{MAX_TITLE_LENGTH}
          </Text>
        </View>

        <View className="bg-white rounded-2xl px-4 py-3 mb-5 border border-gray-200">
          <TextInput
            value={title}
            onChangeText={setTitle}
            placeholder="Give your post a title"
            placeholderTextColor="#9CA3AF"
            maxLength={MAX_TITLE_LENGTH}
            className="text-base"
          />
        </View>

        {/* Description */}
        <Text className="text-sm font-semibold text-gray-700 mb-2">
          Description
        </Text>

        <View className="bg-white rounded-2xl px-4 py-3 mb-5 border border-gray-200">
          <TextInput
            value={description}
            onChangeText={setDescription}
            placeholder="What's happening?"
            placeholderTextColor="#9CA3AF"
            multiline
            textAlignVertical="top"
            className="text-base min-h-[140px]"
          />
        </View>

        {/* Categories */}
        <Text className="text-sm font-semibold text-gray-700 mb-2">
          Categories
        </Text>

        <View className="flex-row flex-wrap mb-5">
          {categories.map((category) => {
            const selected = selectedCategories.includes(category.id);

            return (
              <Pressable
                key={category.id}
                onPress={() => toggleCategory(category.id)}
                className={`mr-2 mb-2 px-4 py-2 rounded-full border ${
                  selected
                    ? "bg-black border-black"
                    : "bg-white border-gray-200"
                }`}
              >
                <Text
                  className={`font-medium ${
                    selected ? "text-white" : "text-gray-700"
                  }`}
                >
                  {category.name}
                </Text>
              </Pressable>
            );
          })}
        </View>

        {/* Age range */}
        <Text className="text-sm font-semibold text-gray-700 mb-2">
          Age range
        </Text>

        <View className="flex-row gap-3 mb-5">
          <View className="flex-1 bg-white rounded-2xl px-4 py-3 border border-gray-200">
            <TextInput
              value={ageFrom}
              onChangeText={setAgeFrom}
              placeholder="From"
              placeholderTextColor="#9CA3AF"
              keyboardType="numeric"
              className="text-base"
            />
          </View>

          <View className="flex-1 bg-white rounded-2xl px-4 py-3 border border-gray-200">
            <TextInput
              value={ageTo}
              onChangeText={setAgeTo}
              placeholder="To"
              placeholderTextColor="#9CA3AF"
              keyboardType="numeric"
              className="text-base"
            />
          </View>
        </View>

        {/* Location */}
        <View className="flex-row items-center mb-2">
          <Ionicons name="location-outline" size={20} color="#6B7280" />
          <Text className="text-sm font-semibold text-gray-700 ml-2">
            Location
          </Text>
        </View>

        <View className="bg-white rounded-2xl overflow-hidden mb-5 border border-gray-200">
          <MapView
            style={{ width: "100%", height: 300 }}
            initialRegion={{
              latitude: 55.6761,
              longitude: 12.5683,
              latitudeDelta: 0.05,
              longitudeDelta: 0.05,
            }}
            showsUserLocation={true}
            onPress={(event) => {
              setSelectedLocation(event.nativeEvent.coordinate);
            }}
          >
            {selectedLocation && <Marker coordinate={selectedLocation} />}
          </MapView>

          <View className="p-4">
            <View className="flex-row items-center">
              <Ionicons
                name="location"
                size={18}
                color={selectedLocation ? "#000" : "#9CA3AF"}
              />

              <Text className="text-gray-600 ml-2 flex-1">
                {selectedLocation
                  ? `${selectedLocation.latitude.toFixed(
                      5
                    )}, ${selectedLocation.longitude.toFixed(5)}`
                  : "Tap the map to choose a location"}
              </Text>
            </View>

            <Pressable
              onPress={getCurrentLocation}
              disabled={gettingLocation}
              className="flex-row items-center mt-4"
            >
              {gettingLocation ? (
                <ActivityIndicator size="small" color="#000" />
              ) : (
                <Ionicons name="locate" size={20} color="#000" />
              )}

              <Text className="font-semibold ml-2">
                {gettingLocation
                  ? "Getting location..."
                  : "Use my current location"}
              </Text>
            </Pressable>
          </View>
        </View>

        {/* Photo URL */}
        <Text className="text-sm font-semibold text-gray-700 mb-2">
          Photo URL
        </Text>

        <View className="bg-white rounded-2xl px-4 py-3 mb-6 border border-gray-200">
          <TextInput
            value={photoURL}
            onChangeText={setPhotoURL}
            placeholder="https://..."
            placeholderTextColor="#9CA3AF"
            autoCapitalize="none"
            keyboardType="url"
            className="text-base"
          />
        </View>

        {/* Create button */}
        <Pressable
          onPress={handleCreatePost}
          disabled={submitting}
          className={`rounded-2xl py-4 items-center ${
            submitting ? "bg-gray-400" : "bg-black"
          }`}
        >
          {submitting ? (
            <ActivityIndicator color="white" />
          ) : (
            <Text className="text-white font-bold text-base">
              Create post
            </Text>
          )}
        </Pressable>
      </ScrollView>
    </View>
  );
}