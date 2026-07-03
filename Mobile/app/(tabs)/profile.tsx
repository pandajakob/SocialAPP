import { Text, View, Image, TouchableOpacity, ScrollView, ActivityIndicator } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import "../../global.css";
import { User } from "@/types/user";
import { useUser } from "@/context/UserContext";
import { useEffect, useState } from "react";
import { useAuth } from "@/context/AuthContext";


export default function ProfileScreen() {

  const { getMe } = useUser()
  const [user, setUser] = useState<User>()
  const { token, loading: authLoading } = useAuth();

  useEffect(() => {
    if (!authLoading && token) {
       getMe()
        .then((u) => setUser(u))
        .catch(e=> {console.log("error getting user", e)})
    }
}, [authLoading, token]);
  

  if (user == undefined) {
    return (
        <View className="flex-1 items-center justify-center bg-white">
          <ActivityIndicator size="large" color="#000000" />
        </View>
    )
  }

  return (
    <ScrollView className="flex-1 bg-white">
      {/* Header / Avatar Section */}
      <View className="items-center pt-20 pb-8 bg-gray-50 border-b border-gray-100">
        <View className="relative">
          { user.profilePhoto?.url ?
          <Image 
            
            source={{ uri: user.profilePhoto.url }} 
            className="w-32 h-32 rounded-full border-4 border-white shadow-sm"
          /> : null}

        </View>
        <Text className="text-2xl font-bold text-gray-900 mt-4">
          {user.firstName} {user.lastName}
        </Text>
        <Text className="text-gray-500">Copenhagen, Denmark</Text>
      </View>

      <View className="px-6 py-8">
        {/* Interests Section (UML: interests: Category[]) */}
        <Text className="text-sm font-bold text-gray-400 uppercase tracking-widest mb-4">Interests</Text>
        <View className="flex-row flex-wrap mb-8">
          {user.interests.map((interest, i) => (
            <View key={i} className="bg-blue-50 px-4 py-2 rounded-full mr-2 mb-2">
              <Text className="text-blue-600 font-medium">{interest}</Text>
            </View>
          ))}
        </View>

        {/* Contact Info Section */}
        <Text className="text-sm font-bold text-gray-400 uppercase tracking-widest mb-4">Account Information</Text>
        
        <View className="space-y-6">
          <View className="flex-row items-center">
            <View className="bg-gray-100 p-3 rounded-2xl mr-4">
              <Ionicons name="mail" size={20} color="#6B7280" />
            </View>
            <View>
              <Text className="text-xs text-gray-400">Email Address</Text>
              <Text className="text-base font-medium text-gray-800">{user.email}</Text>
            </View>
          </View>

          <View className="flex-row items-center mt-6">
            <View className="bg-gray-100 p-3 rounded-2xl mr-4">
              <Ionicons name="call" size={20} color="#6B7280" />
            </View>
            <View>
              <Text className="text-xs text-gray-400">Phone Number</Text>
              <Text className="text-base font-medium text-gray-800">{user.phoneNumber}</Text>
            </View>
          </View>
        </View>

        {/* Action Button */}
        <TouchableOpacity className="mt-10 bg-gray-900 py-4 rounded-2xl items-center">
          <Text className="text-white font-bold text-base">Edit Profile</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}