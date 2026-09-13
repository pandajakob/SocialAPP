import React, { useEffect, useRef, useState } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  TextInput,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  ActivityIndicator,
} from "react-native";
import { useLocalSearchParams, useRouter } from "expo-router";
import { Ionicons } from "@expo/vector-icons";
import { usePost } from "@/context/PostContext";
import { useChat } from "@/context/ChatContext";
import { useUser } from "@/context/UserContext";
import { CATEGORY_EMOJIS } from "@/constants/categoryEmojis";
import { Chat } from "@/types/chat";

export default function ChatScreen() {
  const { postId, chatId } = useLocalSearchParams<{ postId?: string; chatId?: string }>();
  const { feed, userPosts } = usePost();
  const { chats, createChat, sendMessage } = useChat();
  const { user } = useUser();
  const router = useRouter();

  const [message, setMessage] = useState("");
  const [currentChat, setCurrentChat] = useState<Chat | null>(
    () => chats.find((c) => c.id === chatId) ?? null
  );
  const [sending, setSending] = useState(false);
  const scrollRef = useRef<ScrollView>(null);

  
  const post =
    feed.find((p) => p.id === (postId ?? currentChat?.post.id)) ??
    userPosts.find((p) => p.id === (postId ?? currentChat?.post.id));

  const messages = currentChat?.messages ?? [];
  const canSend = message.trim().length > 0 && !sending;

  useEffect(() => {
    if (messages.length > 0) {
      scrollRef.current?.scrollToEnd({ animated: true });
    }
  }, [messages.length]);

  const handleSend = async () => {
    const content = message.trim();
    const resolvedPostId = postId ?? currentChat?.post.id;
    if (!content || !resolvedPostId) return;

    setMessage("");
    setSending(true);

    try {
      const updatedChat = currentChat
        ? await sendMessage(currentChat.id, content)
        : await createChat(resolvedPostId, content);
      setCurrentChat(updatedChat);
    } catch (error) {
      console.log("Error sending message:", error);
    } finally {
      setSending(false);
    }
  };

  return (
    <KeyboardAvoidingView
      className="flex-1 bg-white"
      behavior={Platform.OS === "ios" ? "padding" : "height"}
    >
      {/* Header */}
      <View className="pt-14 pb-4 px-5 flex-row items-center border-b border-gray-100">
        <TouchableOpacity onPress={() => router.back()} className="mr-3">
          <Ionicons name="chevron-back" size={26} color="#111827" />
        </TouchableOpacity>

        <View className="flex-1">
          <Text
            className="text-base font-bold text-gray-900"
            numberOfLines={1}
          >
            {post ? `${post.user.firstName} ${post.user.lastName}` : "Chat"}
          </Text>
          {post && (
            <Text className="text-xs text-gray-400 mt-0.5" numberOfLines={1}>
              {post.title}
            </Text>
          )}
        </View>
      </View>

      {/* Post context card */}
      {post && (
        <TouchableOpacity className="mx-5 mt-4 bg-gray-50 rounded-2xl border border-gray-100 p-4 flex-row items-center"
            onPress={()=> {
            console.log("POSTID: ",post.id);
            console.log(
              postId,
              chatId,
              currentChat,
              chats,
            );
            router.push(`/post/${post.id}`)}}>
            <View className="w-9 h-9 rounded-full bg-white border border-gray-100 items-center justify-center mr-3">
            {post.categories[0] ? (
              <Text className="text-base">
                {CATEGORY_EMOJIS[post.categories[0].name.toLowerCase()] ??
                  "📌"}
              </Text>
            ) : (
              <Ionicons name="document-outline" size={18} color="#6B7280" />
            )}
          </View>

          <View className="flex-1">
            <Text className="text-xs text-gray-400 font-semibold uppercase tracking-widest">
              About post
            </Text>
            <Text
              className="text-gray-900 font-semibold text-sm mt-0.5"
              numberOfLines={1}
            >
              {post.title}
            </Text>
          </View>
        </TouchableOpacity>
      )}

      {/* Messages */}
      <ScrollView
        ref={scrollRef}
        className="flex-1 px-5 pt-4"
        contentContainerStyle={{
          flexGrow: 1,
          justifyContent: messages.length === 0 ? "center" : "flex-end",
          paddingBottom: 8,
        }}
      >
        {messages.length === 0 ? (
          <View className="items-center">
            <Ionicons
              name="chatbubble-ellipses-outline"
              size={44}
              color="#D1D5DB"
            />
            <Text className="text-gray-400 text-sm mt-3 text-center">
              No messages yet.{"\n"}Send the first one!
            </Text>
          </View>
        ) : (
          messages.map((msg, index) => {
            const isMe = msg.sender.id === user?.id;
            const isLast = index === messages.length - 1;
            const msgDate = new Date(msg.createdAt);
            const isToday =
              new Date().toDateString() === msgDate.toDateString();
            const time = isToday
              ? msgDate.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })
              : msgDate.toLocaleDateString([], { month: "short", day: "numeric" }) +
                " " +
                msgDate.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
            return (
              <View
                key={msg.id}
                style={{ alignSelf: isMe ? "flex-end" : "flex-start", maxWidth: "80%" }}
                className="mb-3"
              >
                {!isMe && (
                  <Text className="text-xs text-gray-400 mb-1 ml-1">
                    {msg.sender.firstName}
                  </Text>
                )}
                <View
                  className={`px-4 py-3 ${
                    isMe
                      ? "bg-gray-900 rounded-2xl rounded-tr-sm"
                      : "bg-gray-100 rounded-2xl rounded-tl-sm"
                  }`}
                >
                  <Text
                    className={`text-sm ${isMe ? "text-white" : "text-gray-900"}`}
                  >
                    {msg.content}
                  </Text>
                </View>
                {isLast && (
                  <Text
                    className={`text-xs text-gray-400 mt-1 ${isMe ? "text-right mr-1" : "ml-1"}`}
                  >
                    {time}
                  </Text>
                )}
              </View>
            );
          })
        )}
      </ScrollView>

      {/* Input bar */}
      <View className="px-5 py-4 border-t border-gray-100 flex-row items-end">
        <TextInput
          value={message}
          onChangeText={setMessage}
          placeholder="Type a message..."
          placeholderTextColor="#9CA3AF"
          className="flex-1 bg-gray-50 rounded-2xl px-4 py-3 text-gray-900 border border-gray-100 mr-3"
          multiline
        />
        <TouchableOpacity
          className={`w-11 h-11 rounded-full bg-gray-900 items-center justify-center ${
            canSend ? "" : "opacity-30"
          }`}
          onPress={handleSend}
          disabled={!canSend}
        >
          {sending ? (
            <ActivityIndicator size="small" color="#fff" />
          ) : (
            <Ionicons name="arrow-up" size={18} color="#fff" />
          )}
        </TouchableOpacity>
      </View>
    </KeyboardAvoidingView>
  );
}
