import { createBrowserRouter } from 'react-router-dom';
import LayoutMain from '../layouts/LayoutMain';
import Home from '@/pages/home';

export const routers = createBrowserRouter([
    {
        element: <LayoutMain />,
        children: [
            {
                path: '/',
                element: <Home/>
            }
        ]
    }
]);
