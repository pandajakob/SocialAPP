import { Post } from '../types/post';
import { Chat } from '../types/chat';

export const MOCK_POSTS: Post[] = [
  {
    postId: 1,
    title: "Saturday Basketball",
    description: "Looking for 4 more players for a 5v5 game.",
    location: "Central Park Courts",
    date: "2026-03-12",
    ageFrom: 18,
    ageTo: 35,
    categories: [{ name: "Sports" }],
    photo: { url: "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=400" }
  },
  {
    postId: 2,
    title: "Board Game Meetup",
    description: "Playing Catan and heavy strategy games.",
    location: "Bastard Café",
    date: "2026-03-15",
    ageFrom: 20,
    ageTo: 99,
    categories: [{ name: "Social" }, { name: "Gaming" }],
    photo: { url: "https://images.unsplash.com/photo-1610890716171-6b1bb98ffd09?w=400" }
  }
];

export const MOCK_CHATS: Chat[] = [
  {
    chatId: 1,
    post: MOCK_POSTS[0], // Linked to Saturday Basketball
    lastMessage: "See you at the court! 🏀",
    time: "2m",
    participants: [
      {
        userId: 101,
        firstName: "Alice",
        lastName: "Jensen",
        profilePhoto: { url: "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=400" }
      }
    ]
  },
  {
    chatId: 2,
    post: MOCK_POSTS[1], // Linked to Board Game Meetup
    lastMessage: "I'll bring the extra ball.",
    time: "1h",
    participants: [
      {
        userId: 102,
        firstName: "Bob",
        lastName: "Builder",
        profilePhoto: { url: "https://images.unsplash.com/photo-1610890716171-6b1bb98ffd09?w=400" }
      }
    ]
  },
];

export const mockProfile = {
    firstname: "John",
    lastname: "Doe",
    phone: "+45 12 35 81 32",
    email: "john@doe.dk",
    profilePhoto: { url: "https://i.pravatar.cc/114" },
    interests: ["Sports", "Gaming", "Music"]
  };
