import { ActivityIndicator, View } from "react-native";

export default function LoadingView() {

    return (
        <View className="flex-1 items-center justify-center bg-white">
            <ActivityIndicator size="large" color="#000000" />
        </View>
    )
}