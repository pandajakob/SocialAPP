import { User } from "./user";

export interface UserContextType {
  loading: boolean;
  getMe: () => Promise<User>;
}