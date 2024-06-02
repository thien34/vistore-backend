import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger
} from '@/components/ui/dropdown-menu'
import { User } from 'lucide-react'

const dataAccount = [
    {
        id: 1,
        name: 'Profile'
    },
    {
        id: 2,
        name: 'Logout'
    }
]
const Account = () => {
    return (
        <div>
            <DropdownMenu>
                <DropdownMenuTrigger className='outline-none'>
                    <User strokeWidth={1.5} />
                </DropdownMenuTrigger>
                <DropdownMenuContent align='end' className='mt-2'>
                    <DropdownMenuLabel>My Account</DropdownMenuLabel>
                    <DropdownMenuSeparator />
                    {dataAccount.map((item, index) => (
                        <DropdownMenuItem className='cursor-pointer' key={index}>
                            {item.name}
                        </DropdownMenuItem>
                    ))}
                </DropdownMenuContent>
            </DropdownMenu>
        </div>
    )
}

export default Account
