import {Role} from './role';
import {Tweet} from './tweet';

interface Account {
  id: number;
  username: string;
  password: string;
  email: string;
  location: string;
  website: string;
  picture: string;
  bio: string;
  role: Role;
  tweet: Tweet;
}
