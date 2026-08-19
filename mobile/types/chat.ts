import { Post } from './post';
import { Photo } from './post';

export interface ChatParticipant {
  userId: number;
  firstName: string;
  lastName: string;
  profilePhoto: Photo;
}

export interface Chat {
  chatId: number;
  post: Post;           // Linked Post from your UML
  participants: ChatParticipant[]; // Usually User[2] in your diagram
  lastMessage: string;
  time: string;
}