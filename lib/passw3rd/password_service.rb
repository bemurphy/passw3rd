module Passw3rd
  class PasswordService
    @@password_file_dir = ENV["HOME"]
    @@key_file_dir = ENV["HOME"]

    def self.password_file_dir= dir
      @@password_file_dir = dir
    end

    def self.key_file_dir= dir
      @@key_file_dir = dir
    end    

    def self.get_password (password_file, key_path = @@key_file_dir)
      encoded_password = Base64.decode64(IO.readlines(File.join(@@password_file_dir, password_file)).join)
      decrypt(encoded_password, key_path)
    end

    def self.write_password_file(password, output_path, key_path = @@key_file_dir)
      enc_password = encrypt(password, key_path)
      base64pw = Base64.encode64(enc_password) 
      path = File.join(@@password_file_dir, output_path)
      File.open(path, 'w') { |f| f.write base64pw }
      path
    end

    def self.encrypt(password, key_path = @@key_file_dir)
      raise ArgumentError, "password cannot be blank" if password.to_s.empty?

      cipher = cipher_setup(:encrypt, key_path)
      begin
        e = cipher.update(password)
        e << cipher.final
      rescue OpenSSL::Cipher::CipherError => err
        puts "Couldn't encrypt password."
        raise err
      end
    end

    def self.decrypt(cipher_text, key_path = @@key_file_dir)
      cipher = cipher_setup(:decrypt, key_path)
      begin
        d = cipher.update(cipher_text)
        d << cipher.final
      rescue OpenSSL::Cipher::CipherError => err
        puts "Coudln't decrypt password.  Are you using the right keys?"
        raise err
      end
    end

    protected

    def self.cipher_setup(method, key_path)
      pair = KeyLoader.load(key_path)
      cipher = OpenSSL::Cipher::Cipher.new('aes-128-cbc')
      cipher.send(method)
      cipher.key = pair.key
      cipher.iv = pair.iv
      cipher
    end
  end
end
