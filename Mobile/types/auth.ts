export interface User {
  id: number;
  firstName: string;
  lastName: string;
  age: number;
  interests: string[];
  phoneNumber: string;
  profilePhoto?: {
    url: string;
  };
  email: string;
  role: 'admin' | 'user';
}

export interface AuthContextType {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  loading: boolean;
  login: (email: string, password: string) => Promise<boolean>;
  register: (email: string, password: string) => Promise<boolean>;
  logout: () => Promise<void>;
}