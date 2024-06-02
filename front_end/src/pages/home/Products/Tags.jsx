import clsx from 'clsx'
import PropTypes from 'prop-types'
const Tags = ({ tags, activeTag, handleActiveTag }) => {
    return (
        <div className='flex gap-8 *:text-lg *:text-[#989898] *:cursor-pointer font-medium'>
            {tags.map((tag) => (
                <p
                    onClick={() => handleActiveTag(tag.id)}
                    className={clsx({
                        '!text-primary underline underline-offset-8': tag.id === activeTag
                    })}
                    key={tag.id}
                >
                    {tag.name}
                </p>
            ))}
        </div>
    )
}

export default Tags

Tags.propTypes = {
    tags: PropTypes.array,
    activeTag: PropTypes.number,
    handleActiveTag: PropTypes.func
}
