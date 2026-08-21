import { View, Text, FlatList, TextInput, ActivityIndicator } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import PostCard from '../../components/PostCard';
import CategoryFilter from '@/components/CategoryFilter';
import { usePost } from '@/context/PostContext';
import LoadingView from '@/components/LoadingView';


export default function ExploreScreen() {

const { feed, loading } = usePost();

  if (loading || !feed) {
    return LoadingView()
  }

  return (
    <View className="flex-1 bg-gray-50 px-4 pt-14">
      <Text className="text-3xl font-bold mb-4">Explore</Text>

      {/* Search Header */}
      <View className="flex-row items-center bg-white rounded-2xl px-4 py-3 mb-6 border border-gray-200">
        <Ionicons name="search" size={20} color="#9CA3AF" />
        <TextInput placeholder="Search..." className="flex-1 ml-3" />
      </View>
      <CategoryFilter />

      {feed ?
      <FlatList
        data={feed}
        renderItem={({ item }) => <PostCard  key={item.postId} post={item} />} // Pass data to the child
        keyExtractor={(item) => item.postId}
        contentContainerStyle={{ paddingBottom: 100 }}
      /> : 

      <Text className="text-3xl font-bold mb-4">Error loading feed</Text>
      }

      </View>
  );
}

