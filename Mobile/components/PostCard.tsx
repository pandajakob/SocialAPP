import { View, Text, Image, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { Post } from '../types/post'; // Import your model

interface PostCardProps {
  post: Post;
}

export default function PostCard({ post }: PostCardProps) {
  const hasPhoto = !!post.photoUrl;

  return (
    <TouchableOpacity
      className={
        hasPhoto
          ? "bg-white rounded-3xl mb-5 overflow-hidden border border-gray-100 shadow-sm"
          : "bg-white rounded-3xl mb-3 overflow-hidden border border-gray-100 shadow-sm"
      }
    >
      {hasPhoto && (
        <Image source={{ uri: post.photoUrl }} className="w-full h-48" />
      )}

      <View className={hasPhoto ? "p-4" : "px-4 py-3"}>
        <View className={hasPhoto ? "flex-row mb-2" : "flex-row mb-1"}>
          {post.categories.map((cat, i) => (
            <View key={i} className="bg-blue-100 px-2 py-1 rounded-md mr-2">
              <Text className="text-blue-600 text-xs font-bold uppercase">
                {cat.name}
              </Text>
            </View>
          ))}
        </View>

        <Text className="text-xl font-bold text-gray-900 mb-1">
          {post.title}
        </Text>

        <Text
          className={hasPhoto ? "text-gray-500 text-sm mb-3" : "text-gray-500 text-sm mb-2"}
          numberOfLines={hasPhoto ? 2 : 1}
        >
          {post.description ?? "no description"}
        </Text>

        <View
          className={
            hasPhoto
              ? "flex-row items-center justify-between border-t border-gray-50 pt-3"
              : "flex-row items-center justify-between border-t border-gray-50 pt-2"
          }
        >
          <View className="flex-row items-center">
            <Ionicons name="location-outline" size={16} color="#6B7280" />
            <Text className="text-gray-500 text-xs ml-1">
              {post.location.city}
            </Text>
          </View>
          <Text className="text-gray-500 text-xs">
            Ages: {post.ageFrom}-{post.ageTo}
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
}