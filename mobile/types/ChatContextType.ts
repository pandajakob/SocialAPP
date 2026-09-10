import { Chat } from "./chat";

export interface ChatContextType {
  chats: Chat[];
  loading: boolean;
  createChat: (postId: string, messageContent: string) => Promise<Chat>;
  sendMessage: (chatId: string, messageContent: string) => Promise<Chat>;
}
