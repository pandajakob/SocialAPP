import { Post } from "./post";
import { User } from "./user";

export interface Message {
  id: string;
  sender: User;
  content: string;
}

export interface Chat {
  id: string;
  post: Post;
  messages: Message[];
  participants: User[];
  date: string;
}
