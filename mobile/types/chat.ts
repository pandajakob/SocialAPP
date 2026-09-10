import { Post } from "./post";
import { User } from "./user";

export type MessageState = "SENT" | "READ";

export interface Message {
  id: string;
  sender: User;
  content: string;
  state: MessageState;
}

export interface Chat {
  id: string;
  post: Post;
  messages: Message[];
  participants: User[];
  date: string;
}
