require "open-uri"
require "nokogiri"
require "pp"
require "fileutils"

# Possible fix for buffer overflows
OpenURI::Buffer.send :remove_const, 'StringMax' if OpenURI::Buffer.const_defined?('StringMax')
OpenURI::Buffer.const_set 'StringMax', 0

DOCS_URL = "http://jd.bukkit.org/beta/apidocs/"
DOCS_PATH = File.absolute_path(File.join(File.dirname(__FILE__), "..", "docs"))
TEMPLATE_CLASS = File.absolute_path(File.join(File.dirname(__FILE__), "template_class.html"))
TEMPLATE_INDEX = File.absolute_path(File.join(File.dirname(__FILE__), "template_index.html"))
TEMPLATE_INDEXES = File.absolute_path(File.join(File.dirname(__FILE__), "template_indexes.html"))

class String
 def clean
   self.gsub("\u00A0", " ").strip.gsub(/\n+/, "").gsub(/ +/, " ")
 end

  def uncapitalize 
    self[0, 1].downcase + self[1..-1]
  end
end

def make_class url
  begin
    dom = Nokogiri::HTML(open(url))
    file_path = url.gsub(DOCS_URL, "")
    
    methods = {}

    unless dom.css('tr.TableHeadingColor b:contains("Method Summary")')[0].nil?
      table = dom.css('tr.TableHeadingColor b:contains("Method Summary")')[0].parent.parent.parent.parent
      rtn = nil
      table.css("tr.TableRowColor td").each do |td|
        # puts td
        if rtn.nil? and not td.css("code")[0].nil?
          rtn = td.css("code")[0].content.gsub(/static|public|protected|private|abstract/, "").clean
          unless td.css("code a")[0].nil?
            href = td.css("code a")[0]["href"]
            rtn = "<a href='#{href}'>#{rtn}</a>"
          end
        elsif not td.css("> code > b > a")[0].nil?
          method_name = td.css("> code > b > a")[0].content.clean
          params = td.css("> code").text.clean.gsub(method_name, "").gsub(/[()]/, "").split(", ").map { |e| e.split(" ") }
          td.css("> code").remove
          desc = td.text.clean

          methods[method_name.to_sym] = [] if methods[method_name.to_sym].nil?
          methods[method_name.to_sym] << [desc, rtn, params]
          rtn = nil
        end
      end

      template_class = Nokogiri::HTML(File.read(TEMPLATE_CLASS))

      method_div = template_class.css("div.method")[0].clone
      template_class.css("div.method")[0].remove

      method_tr = template_class.css("table.methods-table > tr")[0].clone
      template_class.css("table.methods-table > tr")[0].remove

      class_name = File.basename(file_path, ".html")
      template_class.css(".class-name").each do |span|
        span.content = class_name
      end

      class_desc = dom.css("p")[0].content
      template_class.css(".class-desc").each do |span|
        span.content = class_desc
      end

      package_name = file_path.gsub(".html", "").gsub("/", ".")
      template_class.css(".domain").each do |span|
        span.content = package_name
      end

      breadcrumb = template_class.css(".breadcrumb")[0]
      breadcrumb_li = template_class.css(".breadcrumb li")[0].clone
      template_class.css(".breadcrumb li")[0].remove
      end_li = template_class.css(".breadcrumb li")[0].clone
      template_class.css(".breadcrumb li")[0].remove
      package_name.split(".")[0...-1].each do |name|
        bli = breadcrumb_li.clone
        bli.content = name
        breadcrumb.add_child(bli)
      end
      breadcrumb.add_child(end_li)

      methods.each do |method|
        tr = method_tr.clone
        tr.css(".return")[0].inner_html = method[1][0][1]

        method[1].each do |meth|
          div = method_div.clone

          div.css(".method-name")[0].content = method[0]
          div.css("p")[0].content = meth[0]
          
          param_str = ""
          first = true
          meth[2].each do |param|
            param_str << ", " unless first
            param_str << "#{param[1]} [#{param[0]}]"
            first = false if first
          end
          div.css(".params")[0].content = param_str

          tr.css("td.details")[0].add_child(div)
        end

        template_class.css("table.methods-table")[0].add_child(tr)
      end

      template_class.css(".class-name-lc").each do |span|
        span.content = class_name.uncapitalize.gsub(".", "")
      end

      FileUtils.mkdir_p(File.dirname(File.join(DOCS_PATH, file_path)))
      File.open(File.join(DOCS_PATH, file_path), "w+") do |f|
        f.write template_class.to_s
      end

      dom = nil
      template_class = nil
      methods = nil

      GC.start
    end
  rescue
    puts "Failed. Retrying"
    make_class url
  end
end

def make_index
  template_index = Nokogiri::HTML(File.read(TEMPLATE_INDEX))
  ul = template_index.css("ul")[0]
  li = ul.css("li")[0].clone
  ul.css("li")[0].remove

  Dir.glob(DOCS_PATH + "/**/*/").each do |dir|
    unless (Dir["#{dir}/*"].select { |f| File.file?(f) }).empty?
      dir_str = dir.gsub(DOCS_PATH + "/", "")
      dir_str = dir_str[0..dir_str.length-2]
      pkg = dir_str.gsub("/", ".")

      dir_li = li.clone
      dir_li.css("a")[0]["href"] = "#{dir_str}/index.html"
      dir_li.css("a")[0].content = pkg

      ul.add_child(dir_li)
    end
  end

    puts "Generating #{File.join(DOCS_PATH, "index.html")}"
  File.open(File.join(DOCS_PATH, "index.html"), "w+") do |f|
    f.write template_index.to_s
  end

  template_index = nil
  GC.start
end

def make_indexes
  template_indexes = Nokogiri::HTML(File.read(TEMPLATE_INDEXES))
  ul = template_indexes.css("ul")[0]
  li = ul.css("li")[0].clone
  ul.css("li")[0].remove

  Dir.glob(DOCS_PATH + "/**/*/").each do |dir|
    index = template_indexes.clone
    (Dir["#{dir}*"].select { |f| File.file?(f) }).each do |clazz|
      href = File.basename(clazz)
      class_name = File.basename(clazz, ".html")

      class_li = li.clone
      class_li.css("a")[0]["href"] = href
      class_li.css("a")[0].content = class_name

      index.css("ul")[0].add_child(class_li)
    end

    puts "Generating #{File.join(dir, "index.html")}"
    File.open(File.join(dir, "index.html"), "w+") do |f|
      f.write index.to_s
    end
  end
end

def make_docs
  puts "Deleting old documentation"
  FileUtils.rm_rf(DOCS_PATH)

  classes = Nokogiri::HTML(open(DOCS_URL + "allclasses-noframe.html"))

  classes.css("table a").each do |clazz|
    puts "Generating #{clazz["href"].gsub(".html", "").gsub("/", ".")}"
    make_class DOCS_URL + clazz["href"]
  end

  make_index
  make_indexes
end

make_docs