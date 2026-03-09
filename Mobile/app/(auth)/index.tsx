import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  KeyboardAvoidingView,
  Platform,
  Alert,
  ActivityIndicator,
} from "react-native";
import { useRouter } from "expo-router";
import { Ionicons } from "@expo/vector-icons";
import { useAuth } from "@/context/AuthContext";

export default function AuthScreen() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
    

  const { login, loading } = useAuth();

  if (loading) {
    return (
      <View className="flex-1 items-center justify-center bg-white">
        <ActivityIndicator size="large" color="#000000" />
      </View>
    );
  }
  
  const handleLogin = async () => {
    console.log("Attempting login with:", { email, password });

    if (!email || !password) {
        console.log(email, password)
        Alert.alert("Error", "Please fill in all fields");
        return;
    }

    const success = await login(email, password);
    if (success) {
      router.dismiss();
    } else {
      Alert.alert("Error", "Invalid credentials");
    }
  };
  return (
    <KeyboardAvoidingView
      behavior={Platform.OS === "ios" ? "padding" : "height"}
      className="flex-1 bg-white justify-center px-8"
    >
      <View className="items-center mb-10">
        <Text className="text-3xl font-bold text-gray-900">Welcome</Text>
        <Text className="text-gray-500 mt-2 text-center">
          Sign in to discover local activities and meet new people.
        </Text>
      </View>

      <View className="space-y-4">
        {/* Email Input */}
        <View>
          <Text className="text-gray-400 text-xs font-bold uppercase tracking-widest mb-2 ml-1">
            Email Address
          </Text>
          <View className="flex-row items-center bg-gray-50 border border-gray-100 rounded-2xl px-4 py-4">
            <Ionicons name="mail-outline" size={20} color="#9CA3AF" />
            <TextInput
              placeholder="name@example.com"
              className="flex-1 ml-3 text-gray-800"
              value={email}
              onChangeText={setEmail}
              autoCapitalize="none"
              keyboardType="email-address"
            />
          </View>
        </View>

        {/* Password Input */}
        <View className="mt-4">
          <Text className="text-gray-400 text-xs font-bold uppercase tracking-widest mb-2 ml-1">
            Password
          </Text>
          <View className="flex-row items-center bg-gray-50 border border-gray-100 rounded-2xl px-4 py-4">
            <Ionicons name="lock-closed-outline" size={20} color="#9CA3AF" />
            <TextInput
              placeholder="••••••••"
              className="flex-1 ml-3 text-gray-800"
              secureTextEntry={!isPasswordVisible}
              value={password}
              onChangeText={setPassword}
            />
            <TouchableOpacity
              onPress={() => setIsPasswordVisible(!isPasswordVisible)}
            >
            <Ionicons
                name={isPasswordVisible ? "eye-off-outline" : "eye-outline"}
                size={20}
                color="#9CA3AF"
            />
            </TouchableOpacity>
          </View>
          <TouchableOpacity className="self-end mt-2">
            {//<Text className="text-xs font-semibold">Forgot Password?</Text>
}
          </TouchableOpacity>
        </View>
      </View>

      {/* Action Buttons */}
      <TouchableOpacity
        className="bg-gray-900 rounded-2xl py-4 mt-8 shadow-md active:opacity-90"
        onPress={handleLogin}
      >
        <Text className="text-white text-center font-bold text-lg">
          Sign In
        </Text>
      </TouchableOpacity>

      <View className="flex-row justify-center mt-8">
        <Text className="text-gray-500">Don't have an account? </Text>
        <TouchableOpacity>
          <Text className="font-bold">Sign Up</Text>
        </TouchableOpacity>
      </View>
    </KeyboardAvoidingView>
  );
}
