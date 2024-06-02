import { Input } from '@/components/ui/input'
import { ScrollArea } from '@/components/ui/scroll-area'

import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger
} from '@/components/ui/dialog'

const dataHistory = [
    {
        id: 1,
        name: 'Iphone 15 pro max'
    },
    {
        id: 2,
        name: 'Macbook air M1'
    }
]

import { History, Search as SearchIcon } from 'lucide-react'
import { useState } from 'react'
// import useDebounce from '@/hooks/useDebounce'
import { Button } from '@/components/ui/button'
import { useNavigate } from 'react-router-dom'
const Search = () => {
    const [search, setSearch] = useState('')
    const onChangeSearch = (e) => setSearch(e.target.value.trimLeft())
    const navigate = useNavigate()
    // const value = useDebounce(search)

    const handleEnter = (e) => {
        if (e.key === 'Enter') handleSeach()
    }
    const handleSeach = () => {
        if (search !== '') {
            navigate('/search?q=' + search)
        }
    }
    const handleAgain = (value) => {
        navigate('/search?q=' + value)
    }
    return (
        <Dialog>
            <DialogTrigger>
                <SearchIcon strokeWidth={1.5} />
            </DialogTrigger>
            <DialogContent className='text-foreground !outline-none rounded-3xl max-w-3xl'>
                <DialogHeader>
                    <DialogTitle className='text-foreground focus-within:outline-none text-3xl font-normal mb-2'>
                        Can we help you with your search?
                    </DialogTitle>
                    <div className='flex items-center rounded-full border'>
                        <Input
                            className='border-none shadow-none py-4 !ring-0 h-full px-6 text-lg font-light placeholder:text-base   '
                            placeholder='Search...'
                            value={search}
                            onChange={onChangeSearch}
                            onKeyDown={handleEnter}
                        />
                        <Button onClick={handleSeach} className='rounded-full p-0 size-10 flex-shrink-0 m-1'>
                            <SearchIcon />
                        </Button>
                    </div>
                </DialogHeader>
                <DialogDescription className='text-foreground/70 px-4'>
                    <ScrollArea className='h-[150px] text-base w-full mt-2'>
                        <ul>
                            {dataHistory.map((item, index) => {
                                return (
                                    <li
                                        onClick={handleAgain.bind(null, item.name)}
                                        key={index}
                                        className='flex items-center gap-2 hover:bg-gray-50 py-2 px-2 rounded-xl cursor-pointer'
                                    >
                                        <History className='size-5' />
                                        <span>{item.name}</span>
                                    </li>
                                )
                            })}
                        </ul>
                    </ScrollArea>
                </DialogDescription>
            </DialogContent>
        </Dialog>
    )
}

export default Search
