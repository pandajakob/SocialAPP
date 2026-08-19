import { Post } from "./post";

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  age: number;
  interests: string[];
  phoneNumber: string;
  profilePhoto?: {
    url: string;
  };
  email: string;
}
