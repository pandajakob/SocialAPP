import { Text, View, Image, TouchableOpacity, ScrollView } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import "../../global.css";
import { mockProfile as MOCK_PROFILE } from "@/constants/MockData";
export default function Profile() {

  return (
    <ScrollView className="flex-1 bg-white">
      {/* Header / Avatar Section */}
      <View className="items-center pt-20 pb-8 bg-gray-50 border-b border-gray-100">
        <View className="relative">
          <Image 
            source={{ uri: MOCK_PROFILE.profilePhoto.url }} 
            className="w-32 h-32 rounded-full border-4 border-white shadow-sm"
          />

        </View>
        <Text className="text-2xl font-bold text-gray-900 mt-4">
          {MOCK_PROFILE.firstname} {MOCK_PROFILE.lastname}
        </Text>
        <Text className="text-gray-500">Copenhagen, Denmark</Text>
      </View>

      <View className="px-6 py-8">
        {/* Interests Section (UML: interests: Category[]) */}
        <Text className="text-sm font-bold text-gray-400 uppercase tracking-widest mb-4">Interests</Text>
        <View className="flex-row flex-wrap mb-8">
          {MOCK_PROFILE.interests.map((interest, i) => (
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
              <Text className="text-base font-medium text-gray-800">{MOCK_PROFILE.email}</Text>
            </View>
          </View>

          <View className="flex-row items-center mt-6">
            <View className="bg-gray-100 p-3 rounded-2xl mr-4">
              <Ionicons name="call" size={20} color="#6B7280" />
            </View>
            <View>
              <Text className="text-xs text-gray-400">Phone Number</Text>
              <Text className="text-base font-medium text-gray-800">{MOCK_PROFILE.phone}</Text>
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