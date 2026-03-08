export interface Photo {
  url: string;
}

export interface Category {
  name: string;
}

export interface Post {
  postId: number;
  title: string;
  description: string;
  location: string;
  date: string;
  ageFrom: number;
  ageTo: number;
  categories: Category[];
  photo: Photo;
}