

export interface Category {
  id: number;
  name: string;
  parentCategoryId: number

}

export interface Location {
  latitude: number;
  longitude: number;
  country: string;
  city: string;
  formattedAddress: string;
}

export interface Post {
  postId: number;
  title: string;
  description: string;
  location: Location;
  date: string;
  ageFrom: number;
  ageTo: number;
  categories: Category[];
  photoUrl: string;
}