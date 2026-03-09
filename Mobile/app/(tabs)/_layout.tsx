import React from "react";
import { NativeTabs, Icon, Label, VectorIcon } from 'expo-router/unstable-native-tabs';
import { Ionicons } from "@expo/vector-icons";

import { HapticTab } from "@/components/haptic-tab";
import { Colors } from "@/constants/theme";
import { useColorScheme } from "@/hooks/use-color-scheme";

export default function TabLayout() {
  const colorScheme = useColorScheme();
  const tint = Colors[colorScheme ?? "light"].tint;

  return (
    <NativeTabs
      tintColor={tint}>
      <NativeTabs.Trigger name="index">
        <Label>Explore</Label>
        <Icon src={<VectorIcon family={Ionicons} name="search" />} />
      </NativeTabs.Trigger>

      <NativeTabs.Trigger name="map">
        <Label>Map</Label>
        <Icon src={<VectorIcon family={Ionicons} name="map" />} />
      </NativeTabs.Trigger>

      <NativeTabs.Trigger name="chats">
        <Label>Chats</Label>
        <Icon src={<VectorIcon family={Ionicons} name="chatbubbles" />} />
      </NativeTabs.Trigger>

      <NativeTabs.Trigger name="profile">
        <Label>Profile</Label>
        <Icon src={<VectorIcon family={Ionicons} name="person" />} />
      </NativeTabs.Trigger>
    </NativeTabs>
  );
}