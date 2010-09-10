require 'openssl'

class KeyLoader
  KEY_FILE = "/.atti-pw-encryptionKey"
  IV_FILE = "/.atti-pw-encryptionIV"
  
  
  def self.load(path=nil) 
    if path.nil?
      path = ENV['HOME']
    end
    
    begin
      key = IO.readlines(File.expand_path(path + KEY_FILE))[0]
      iv = IO.readlines(File.expand_path(path + IV_FILE))[0]
    rescue Errno::ENOENT
      puts "Couldn't read key/iv from #{path}.  Have they been generated?\n"
      raise $!
    end
    
    pair = KeyIVPair.new
    pair.key = [key].pack("H*")
    pair.iv = [iv].pack("H*")
    pair
  end
  
  def self.create_key_iv_file(path=nil)
    if path.nil?
      path = ENV['HOME']
    end    
    
    cipher = OpenSSL::Cipher::Cipher.new('aes-128-cbc')
    iv = cipher.random_iv
    key = cipher.random_key
    
    begin
      File.open(path + KEY_FILE, 'w') {|f| f.write(key.unpack("H*")) }
      File.open(path + IV_FILE, 'w') {|f| f.write(iv.unpack("H*")) }
    rescue
      puts "Couldn't write key/IV to #{path}\n"
      raise $!
    end
    
  end
end

class KeyIVPair
  attr_accessor :key, :iv
end