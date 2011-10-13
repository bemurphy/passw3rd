$LOAD_PATH.unshift File.expand_path(File.dirname(__FILE__) + '/lib')

require 'passw3rd/version'

Gem::Specification.new do |s|
  s.name        = 'passw3rd'
  s.platform    = Gem::Platform::RUBY
  s.version     = Passw3rd::VERSION
  s.summary     = %q{A simple "keep the passwords out of source code and config files".}
  s.description = %q{Generate a key/iv file, generate passwords, and store encrypted files in source control, keep the key/iv safe!}
  s.authors     = ['Neil Matatall']
  s.email       = ['neil.matatall@gmail.com']
  s.homepage    = 'https://github.com/oreoshake/passw3rd'
  
  s.files       = %w( README.md LICENSE EXAMPLES.md History.txt )
  s.files       += Dir.glob("lib/**/*")
  s.files       += Dir.glob("bin/**/*")
  s.files       += Dir.glob("test/**/*")
  s.executables = [ "passw3rd" ]
end
