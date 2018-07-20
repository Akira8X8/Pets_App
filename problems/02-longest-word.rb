# Write a method that takes in a string. Return the longest word in
# the string. You may assume that the string contains only letters and
# spaces.
#
# You may use the String `split` method to aid you in your quest.
#
# Difficulty: easy.

def longest_word(sentence)
	
	pieces = sentence.split(" ")
	longest = []
	idx = 0
		
	while idx < pieces.length
		deck = pieces[idx]
		
		if deck == nil
			deck = pieces
		elsif deck.length < pieces[idx].length
			
			longest = deck		
			puts(longest)
			
		end
		idx += 1
		
	end
	
	return longest
	
	
end

# These are tests to check that your code is working. After writing
# your solution, they should all print true.

puts(
  'longest_word("short longest") == "longest": ' +
  (longest_word('short longest') == 'longest').to_s
)
puts(
  'longest_word("one") == "one": ' +
  (longest_word('one') == 'one').to_s
)
puts(
  'longest_word("abc def abcde") == "abcde": ' +
  (longest_word('abc def abcde') == 'abcde').to_s
)
