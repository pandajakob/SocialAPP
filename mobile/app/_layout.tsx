import React from "react";
import { DarkTheme, DefaultTheme, ThemeProvider } from "expo-router/react-navigation";
import { Stack } from "expo-router";
import { StatusBar } from "expo-status-bar";
import "react-native-reanimated";

import { useColorScheme } from "@/hooks/use-color-scheme";
import { AuthProvider } from "@/context/AuthContext";
import { UserProvider } from "@/context/UserContext";
import { PostProvider } from "@/context/PostContext";
import { CategoryProvider } from "@/context/CategoryContext";

export const unstable_settings = {
  anchor: "(tabs)",
};

export default function RootLayout() {
  const colorScheme = useColorScheme();

  return (
    <AuthProvider>
      <UserProvider>
        <PostProvider>
          <CategoryProvider>
          <ThemeProvider value={colorScheme === "dark" ? DarkTheme : DefaultTheme}>
            <Stack>
              <Stack.Screen name="(auth)" options={{ headerShown: false }} />
              <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
              <Stack.Screen
                name="createPost"
                options={{ presentation: "modal", title: "createPost" }}
              />

            </Stack>
            <StatusBar style="auto" />
          </ThemeProvider>
          </CategoryProvider>
      </PostProvider>
    </UserProvider>
    </AuthProvider>
  );
}
